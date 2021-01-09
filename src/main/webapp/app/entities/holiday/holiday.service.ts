import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHoliday } from 'app/shared/model/holiday.model';

type EntityResponseType = HttpResponse<IHoliday>;
type EntityArrayResponseType = HttpResponse<IHoliday[]>;

@Injectable({ providedIn: 'root' })
export class HolidayService {
  public resourceUrl = SERVER_API_URL + 'api/holidays';

  constructor(protected http: HttpClient) {}

  create(holiday: IHoliday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(holiday);
    return this.http
      .post<IHoliday>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(holiday: IHoliday): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(holiday);
    return this.http
      .put<IHoliday>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHoliday>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHoliday[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(holiday: IHoliday): IHoliday {
    const copy: IHoliday = Object.assign({}, holiday, {
      from: holiday.from && holiday.from.isValid() ? holiday.from.format(DATE_FORMAT) : undefined,
      to: holiday.to && holiday.to.isValid() ? holiday.to.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.from = res.body.from ? moment(res.body.from) : undefined;
      res.body.to = res.body.to ? moment(res.body.to) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((holiday: IHoliday) => {
        holiday.from = holiday.from ? moment(holiday.from) : undefined;
        holiday.to = holiday.to ? moment(holiday.to) : undefined;
      });
    }
    return res;
  }
}
