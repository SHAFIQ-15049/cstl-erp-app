import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOverTime } from 'app/shared/model/over-time.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

type EntityResponseType = HttpResponse<IOverTime>;
type EntityArrayResponseType = HttpResponse<IOverTime[]>;

@Injectable({ providedIn: 'root' })
export class OverTimeService {
  public resourceUrl = SERVER_API_URL + 'api/over-times';

  constructor(protected http: HttpClient) {}

  create(overTime: IOverTime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overTime);
    return this.http
      .post<IOverTime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(overTime: IOverTime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overTime);
    return this.http
      .put<IOverTime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOverTime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOverTime[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  generateOverTimes(year?: number, month?: MonthType): Observable<EntityArrayResponseType> {
    return this.http
      .get<IOverTime[]>(`${this.resourceUrl}/generate-over-time/${year}/${month}`, {
        observe: 'response',
      })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  regenerateOverTimes(year?: number, month?: MonthType): Observable<EntityArrayResponseType> {
    return this.http
      .get<IOverTime[]>(`${this.resourceUrl}/regenerate-over-time/${year}/${month}`, {
        observe: 'response',
      })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  regenerateEmployeeOverTime(overTimeId?: number): Observable<EntityResponseType> {
    return this.http
      .get<IOverTime>(`${this.resourceUrl}/regenerate-employee-over-time/${overTimeId}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(overTime: IOverTime): IOverTime {
    const copy: IOverTime = Object.assign({}, overTime, {
      fromDate: overTime.fromDate && overTime.fromDate.isValid() ? overTime.fromDate.toJSON() : undefined,
      toDate: overTime.toDate && overTime.toDate.isValid() ? overTime.toDate.toJSON() : undefined,
      executedOn: overTime.executedOn && overTime.executedOn.isValid() ? overTime.executedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
      res.body.executedOn = res.body.executedOn ? moment(res.body.executedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((overTime: IOverTime) => {
        overTime.fromDate = overTime.fromDate ? moment(overTime.fromDate) : undefined;
        overTime.toDate = overTime.toDate ? moment(overTime.toDate) : undefined;
        overTime.executedOn = overTime.executedOn ? moment(overTime.executedOn) : undefined;
      });
    }
    return res;
  }
}
