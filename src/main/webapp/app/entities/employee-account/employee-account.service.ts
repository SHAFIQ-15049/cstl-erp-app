import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeAccount } from 'app/shared/model/employee-account.model';

type EntityResponseType = HttpResponse<IEmployeeAccount>;
type EntityArrayResponseType = HttpResponse<IEmployeeAccount[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeAccountService {
  public resourceUrl = SERVER_API_URL + 'api/employee-accounts';

  constructor(protected http: HttpClient) {}

  create(employeeAccount: IEmployeeAccount): Observable<EntityResponseType> {
    return this.http.post<IEmployeeAccount>(this.resourceUrl, employeeAccount, { observe: 'response' });
  }

  update(employeeAccount: IEmployeeAccount): Observable<EntityResponseType> {
    return this.http.put<IEmployeeAccount>(this.resourceUrl, employeeAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
