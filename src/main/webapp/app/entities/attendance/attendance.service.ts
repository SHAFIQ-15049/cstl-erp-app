import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendance } from 'app/shared/model/attendance.model';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceService {
  public resourceUrl = SERVER_API_URL + 'api/attendances';

  constructor(protected http: HttpClient) {}

  create(attendance: IAttendance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendance);
    return this.http
      .post<IAttendance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendance: IAttendance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendance);
    return this.http
      .put<IAttendance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendance: IAttendance): IAttendance {
    const copy: IAttendance = Object.assign({}, attendance, {
      attendanceDate:
        attendance.attendanceDate && attendance.attendanceDate.isValid() ? attendance.attendanceDate.format(DATE_FORMAT) : undefined,
      attendanceTime: attendance.attendanceTime && attendance.attendanceTime.isValid() ? attendance.attendanceTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.attendanceDate = res.body.attendanceDate ? moment(res.body.attendanceDate) : undefined;
      res.body.attendanceTime = res.body.attendanceTime ? moment(res.body.attendanceTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendance: IAttendance) => {
        attendance.attendanceDate = attendance.attendanceDate ? moment(attendance.attendanceDate) : undefined;
        attendance.attendanceTime = attendance.attendanceTime ? moment(attendance.attendanceTime) : undefined;
      });
    }
    return res;
  }
}
