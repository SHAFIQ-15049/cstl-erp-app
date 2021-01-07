import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDefaultAllowance } from 'app/shared/model/default-allowance.model';

type EntityResponseType = HttpResponse<IDefaultAllowance>;
type EntityArrayResponseType = HttpResponse<IDefaultAllowance[]>;

@Injectable({ providedIn: 'root' })
export class DefaultAllowanceService {
  public resourceUrl = SERVER_API_URL + 'api/default-allowances';

  constructor(protected http: HttpClient) {}

  create(defaultAllowance: IDefaultAllowance): Observable<EntityResponseType> {
    return this.http.post<IDefaultAllowance>(this.resourceUrl, defaultAllowance, { observe: 'response' });
  }

  update(defaultAllowance: IDefaultAllowance): Observable<EntityResponseType> {
    return this.http.put<IDefaultAllowance>(this.resourceUrl, defaultAllowance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDefaultAllowance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDefaultAllowance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
