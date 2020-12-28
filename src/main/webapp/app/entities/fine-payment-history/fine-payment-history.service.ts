import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFinePaymentHistory } from 'app/shared/model/fine-payment-history.model';

type EntityResponseType = HttpResponse<IFinePaymentHistory>;
type EntityArrayResponseType = HttpResponse<IFinePaymentHistory[]>;

@Injectable({ providedIn: 'root' })
export class FinePaymentHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/fine-payment-histories';

  constructor(protected http: HttpClient) {}

  create(finePaymentHistory: IFinePaymentHistory): Observable<EntityResponseType> {
    return this.http.post<IFinePaymentHistory>(this.resourceUrl, finePaymentHistory, { observe: 'response' });
  }

  update(finePaymentHistory: IFinePaymentHistory): Observable<EntityResponseType> {
    return this.http.put<IFinePaymentHistory>(this.resourceUrl, finePaymentHistory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFinePaymentHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFinePaymentHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
