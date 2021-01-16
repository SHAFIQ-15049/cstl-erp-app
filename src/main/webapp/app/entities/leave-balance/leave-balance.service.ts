import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';

type EntityResponseType = HttpResponse<ILeaveBalance>;
type EntityArrayResponseType = HttpResponse<ILeaveBalance[]>;

@Injectable({ providedIn: 'root' })
export class LeaveBalanceService {
  public resourceUrl = SERVER_API_URL + 'api/leave-balances';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILeaveBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILeaveBalance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
