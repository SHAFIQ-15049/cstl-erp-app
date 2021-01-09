import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFine } from 'app/shared/model/fine.model';
import { SessionStorageService } from 'ngx-webstorage';

type EntityResponseType = HttpResponse<IFine>;
type EntityArrayResponseType = HttpResponse<IFine[]>;

@Injectable({ providedIn: 'root' })
export class FineService {
  public resourceUrl = SERVER_API_URL + 'api/fines';

  private fineId = 'fineId';

  constructor(protected http: HttpClient, private $sessionStorage: SessionStorageService) {}

  storeFineId(fineId: number): void {
    this.$sessionStorage.store(this.fineId, fineId);
  }

  getFineId(): number | null | undefined {
    return this.$sessionStorage.retrieve(this.fineId);
  }
  create(fine: IFine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fine);
    return this.http
      .post<IFine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fine: IFine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fine);
    return this.http
      .put<IFine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFine[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fine: IFine): IFine {
    const copy: IFine = Object.assign({}, fine, {
      finedOn: fine.finedOn && fine.finedOn.isValid() ? fine.finedOn.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.finedOn = res.body.finedOn ? moment(res.body.finedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fine: IFine) => {
        fine.finedOn = fine.finedOn ? moment(fine.finedOn) : undefined;
      });
    }
    return res;
  }
}
