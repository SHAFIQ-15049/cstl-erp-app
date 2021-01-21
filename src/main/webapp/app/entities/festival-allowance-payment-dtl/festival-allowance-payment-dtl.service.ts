import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';

type EntityResponseType = HttpResponse<IFestivalAllowancePaymentDtl>;
type EntityArrayResponseType = HttpResponse<IFestivalAllowancePaymentDtl[]>;

@Injectable({ providedIn: 'root' })
export class FestivalAllowancePaymentDtlService {
  public resourceUrl = SERVER_API_URL + 'api/festival-allowance-payment-dtls';

  constructor(protected http: HttpClient) {}

  create(festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(festivalAllowancePaymentDtl);
    return this.http
      .post<IFestivalAllowancePaymentDtl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(festivalAllowancePaymentDtl);
    return this.http
      .put<IFestivalAllowancePaymentDtl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFestivalAllowancePaymentDtl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFestivalAllowancePaymentDtl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl): IFestivalAllowancePaymentDtl {
    const copy: IFestivalAllowancePaymentDtl = Object.assign({}, festivalAllowancePaymentDtl, {
      executedOn:
        festivalAllowancePaymentDtl.executedOn && festivalAllowancePaymentDtl.executedOn.isValid()
          ? festivalAllowancePaymentDtl.executedOn.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.executedOn = res.body.executedOn ? moment(res.body.executedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl) => {
        festivalAllowancePaymentDtl.executedOn = festivalAllowancePaymentDtl.executedOn
          ? moment(festivalAllowancePaymentDtl.executedOn)
          : undefined;
      });
    }
    return res;
  }
}
