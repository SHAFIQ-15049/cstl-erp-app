import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeaveType } from 'app/shared/model/leave-type.model';

type EntityResponseType = HttpResponse<ILeaveType>;
type EntityArrayResponseType = HttpResponse<ILeaveType[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeService {
  public resourceUrl = SERVER_API_URL + 'api/leave-types';

  constructor(protected http: HttpClient) {}

  create(leaveType: ILeaveType): Observable<EntityResponseType> {
    return this.http.post<ILeaveType>(this.resourceUrl, leaveType, { observe: 'response' });
  }

  update(leaveType: ILeaveType): Observable<EntityResponseType> {
    return this.http.put<ILeaveType>(this.resourceUrl, leaveType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILeaveType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILeaveType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
