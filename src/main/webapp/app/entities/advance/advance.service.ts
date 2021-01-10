import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdvance } from 'app/shared/model/advance.model';
import { SessionStorageService } from 'ngx-webstorage';

type EntityResponseType = HttpResponse<IAdvance>;
type EntityArrayResponseType = HttpResponse<IAdvance[]>;

@Injectable({ providedIn: 'root' })
export class AdvanceService {
  public resourceUrl = SERVER_API_URL + 'api/advances';

  private advanceId = 'advanceId';

  constructor(protected http: HttpClient, private $sessionStorage: SessionStorageService) {}

  storeAdvanceId(advanceId: number): void {
    this.$sessionStorage.store(this.advanceId, advanceId);
  }

  getAdvanceId(): number | null | undefined {
    return this.$sessionStorage.retrieve(this.advanceId);
  }

  create(advance: IAdvance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(advance);
    return this.http
      .post<IAdvance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(advance: IAdvance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(advance);
    return this.http
      .put<IAdvance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAdvance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdvance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(advance: IAdvance): IAdvance {
    const copy: IAdvance = Object.assign({}, advance, {
      providedOn: advance.providedOn && advance.providedOn.isValid() ? advance.providedOn.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.providedOn = res.body.providedOn ? moment(res.body.providedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((advance: IAdvance) => {
        advance.providedOn = advance.providedOn ? moment(advance.providedOn) : undefined;
      });
    }
    return res;
  }
}
