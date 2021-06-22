import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomer } from 'app/shared/model/customer.model';

type EntityResponseType = HttpResponse<ICustomer>;
type EntityArrayResponseType = HttpResponse<ICustomer[]>;

@Injectable({ providedIn: 'root' })
export class CustomerService {
  public resourceUrl = SERVER_API_URL + 'api/customers';

  constructor(protected http: HttpClient) {}

  create(customer: ICustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .post<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customer: ICustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .put<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customer: ICustomer): ICustomer {
    const copy: ICustomer = Object.assign({}, customer, {
      dateOfBirth: customer.dateOfBirth && customer.dateOfBirth.isValid() ? customer.dateOfBirth.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? moment(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customer: ICustomer) => {
        customer.dateOfBirth = customer.dateOfBirth ? moment(customer.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
