import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeekendDateMap } from 'app/shared/model/weekend-date-map.model';

type EntityResponseType = HttpResponse<IWeekendDateMap>;
type EntityArrayResponseType = HttpResponse<IWeekendDateMap[]>;

@Injectable({ providedIn: 'root' })
export class WeekendDateMapService {
  public resourceUrl = SERVER_API_URL + 'api/weekend-date-maps';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWeekendDateMap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  fetchByYear(year: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IWeekendDateMap[]>(`${this.resourceUrl}/year/${year}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  fetchByYearAndMonth(year: number, month: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IWeekendDateMap[]>(`${this.resourceUrl}/year/${year}/month/${month}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWeekendDateMap[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(weekendDateMap: IWeekendDateMap): IWeekendDateMap {
    const copy: IWeekendDateMap = Object.assign({}, weekendDateMap, {
      weekendDate:
        weekendDateMap.weekendDate && weekendDateMap.weekendDate.isValid() ? weekendDateMap.weekendDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.weekendDate = res.body.weekendDate ? moment(res.body.weekendDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((weekendDateMap: IWeekendDateMap) => {
        weekendDateMap.weekendDate = weekendDateMap.weekendDate ? moment(weekendDateMap.weekendDate) : undefined;
      });
    }
    return res;
  }
}
