import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';

type EntityResponseType = HttpResponse<IAdvancePaymentHistory>;
type EntityArrayResponseType = HttpResponse<IAdvancePaymentHistory[]>;

@Injectable({ providedIn: 'root' })
export class AdvancePaymentHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/advance-payment-histories';

  constructor(protected http: HttpClient) {}

  create(advancePaymentHistory: IAdvancePaymentHistory): Observable<EntityResponseType> {
    return this.http.post<IAdvancePaymentHistory>(this.resourceUrl, advancePaymentHistory, { observe: 'response' });
  }

  update(advancePaymentHistory: IAdvancePaymentHistory): Observable<EntityResponseType> {
    return this.http.put<IAdvancePaymentHistory>(this.resourceUrl, advancePaymentHistory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdvancePaymentHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdvancePaymentHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
