import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { SessionStorageService } from 'ngx-webstorage';

type EntityResponseType = HttpResponse<IFestivalAllowancePayment>;
type EntityArrayResponseType = HttpResponse<IFestivalAllowancePayment[]>;

@Injectable({ providedIn: 'root' })
export class FestivalAllowancePaymentService {
  public resourceUrl = SERVER_API_URL + 'api/festival-allowance-payments';

  private festivalAllowanceId = 'festivalAllowanceId';

  constructor(protected http: HttpClient, private $sessionStorage: SessionStorageService) {}

  storeFestivalAllowanceId(festivalAllowanceId: number): void {
    this.$sessionStorage.store(this.festivalAllowanceId, festivalAllowanceId);
  }

  getFestivalAllowanceId(): number | null | undefined {
    return this.$sessionStorage.retrieve(this.festivalAllowanceId);
  }

  create(festivalAllowancePayment: IFestivalAllowancePayment): Observable<EntityResponseType> {
    if (festivalAllowancePayment.id) return this.regenerate(festivalAllowancePayment);
    const copy = this.convertDateFromClient(festivalAllowancePayment);
    return this.http
      .post<IFestivalAllowancePayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  regenerate(festivalAllowancePayment: IFestivalAllowancePayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(festivalAllowancePayment);
    return this.http
      .post<IFestivalAllowancePayment>(this.resourceUrl + '/regenerate', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(festivalAllowancePayment: IFestivalAllowancePayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(festivalAllowancePayment);
    return this.http
      .put<IFestivalAllowancePayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFestivalAllowancePayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFestivalAllowancePayment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(festivalAllowancePayment: IFestivalAllowancePayment): IFestivalAllowancePayment {
    const copy: IFestivalAllowancePayment = Object.assign({}, festivalAllowancePayment, {
      executedOn:
        festivalAllowancePayment.executedOn && festivalAllowancePayment.executedOn.isValid()
          ? festivalAllowancePayment.executedOn.toJSON()
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
      res.body.forEach((festivalAllowancePayment: IFestivalAllowancePayment) => {
        festivalAllowancePayment.executedOn = festivalAllowancePayment.executedOn ? moment(festivalAllowancePayment.executedOn) : undefined;
      });
    }
    return res;
  }
}
