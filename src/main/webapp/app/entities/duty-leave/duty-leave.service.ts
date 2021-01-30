import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDutyLeave } from 'app/shared/model/duty-leave.model';

type EntityResponseType = HttpResponse<IDutyLeave>;
type EntityArrayResponseType = HttpResponse<IDutyLeave[]>;

@Injectable({ providedIn: 'root' })
export class DutyLeaveService {
  public resourceUrl = SERVER_API_URL + 'api/duty-leaves';

  constructor(protected http: HttpClient) {}

  update(dutyLeaves: IDutyLeave[]): Observable<EntityArrayResponseType> {
    const copy: IDutyLeave[] = [];
    for (let i = 0; i < dutyLeaves.length; i++) {
      copy.push(this.convertDateFromClient(dutyLeaves[i]));
    }
    return this.http
      .put<IDutyLeave[]>(`${this.resourceUrl}`, copy, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDutyLeave>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDutyLeave[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dutyLeave: IDutyLeave): IDutyLeave {
    const copy: IDutyLeave = Object.assign({}, dutyLeave, {
      fromDate: dutyLeave.fromDate && dutyLeave.fromDate.isValid() ? dutyLeave.fromDate.format(DATE_FORMAT) : undefined,
      toDate: dutyLeave.toDate && dutyLeave.toDate.isValid() ? dutyLeave.toDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dutyLeave: IDutyLeave) => {
        dutyLeave.fromDate = dutyLeave.fromDate ? moment(dutyLeave.fromDate) : undefined;
        dutyLeave.toDate = dutyLeave.toDate ? moment(dutyLeave.toDate) : undefined;
      });
    }
    return res;
  }
}
