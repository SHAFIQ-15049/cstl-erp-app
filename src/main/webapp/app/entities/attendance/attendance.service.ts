import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendance } from 'app/shared/model/attendance.model';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceService {
  public resourceUrl = SERVER_API_URL + 'api/attendances';

  constructor(protected http: HttpClient) {}

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

  protected convertDateFromClient(attendance: IAttendance): IAttendance {
    const copy: IAttendance = Object.assign({}, attendance, {
      attendanceTime: attendance.attendanceTime && attendance.attendanceTime.isValid() ? attendance.attendanceTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.attendanceTime = res.body.attendanceTime ? moment(res.body.attendanceTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendance: IAttendance) => {
        attendance.attendanceTime = attendance.attendanceTime ? moment(attendance.attendanceTime) : undefined;
      });
    }
    return res;
  }
}
