import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';

type EntityResponseType = HttpResponse<IAttendanceSummary>;
type EntityArrayResponseType = HttpResponse<IAttendanceSummary[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceSummaryService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-summaries';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendanceSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendanceSummary[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByEmployeeIdFromAndToDate(employeeId: number, fromDate: string, toDate: string): Observable<EntityArrayResponseType> {
    return this.http
      .get<IAttendanceSummary[]>(`${this.resourceUrl}/employeeId/${employeeId}/fromDate/${fromDate}/toDate/${toDate}`, {
        observe: 'response',
      })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByFromAndToDate(fromDate: string, toDate: string): Observable<EntityArrayResponseType> {
    return this.http
      .get<IAttendanceSummary[]>(`${this.resourceUrl}/fromDate/${fromDate}/toDate/${toDate}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(attendanceSummary: IAttendanceSummary): IAttendanceSummary {
    const copy: IAttendanceSummary = Object.assign({}, attendanceSummary, {
      inTime: attendanceSummary.inTime && attendanceSummary.inTime.isValid() ? attendanceSummary.inTime.toJSON() : undefined,
      outTime: attendanceSummary.outTime && attendanceSummary.outTime.isValid() ? attendanceSummary.outTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inTime = res.body.inTime ? moment(res.body.inTime) : undefined;
      res.body.outTime = res.body.outTime ? moment(res.body.outTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendanceSummary: IAttendanceSummary) => {
        attendanceSummary.inTime = attendanceSummary.inTime ? moment(attendanceSummary.inTime) : undefined;
        attendanceSummary.outTime = attendanceSummary.outTime ? moment(attendanceSummary.outTime) : undefined;
      });
    }
    return res;
  }
}
