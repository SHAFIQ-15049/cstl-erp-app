import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdvance } from 'app/shared/model/advance.model';

type EntityResponseType = HttpResponse<IAdvance>;
type EntityArrayResponseType = HttpResponse<IAdvance[]>;

@Injectable({ providedIn: 'root' })
export class AdvanceService {
  public resourceUrl = SERVER_API_URL + 'api/advances';

  constructor(protected http: HttpClient) {}

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
      paidOn: advance.paidOn && advance.paidOn.isValid() ? advance.paidOn.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paidOn = res.body.paidOn ? moment(res.body.paidOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((advance: IAdvance) => {
        advance.paidOn = advance.paidOn ? moment(advance.paidOn) : undefined;
      });
    }
    return res;
  }
}
