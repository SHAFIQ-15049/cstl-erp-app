import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceHistory } from 'app/shared/model/service-history.model';

type EntityResponseType = HttpResponse<IServiceHistory>;
type EntityArrayResponseType = HttpResponse<IServiceHistory[]>;

@Injectable({ providedIn: 'root' })
export class ServiceHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/service-histories';

  constructor(protected http: HttpClient) {}

  create(serviceHistory: IServiceHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceHistory);
    return this.http
      .post<IServiceHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceHistory: IServiceHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceHistory);
    return this.http
      .put<IServiceHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceHistory: IServiceHistory): IServiceHistory {
    const copy: IServiceHistory = Object.assign({}, serviceHistory, {
      from: serviceHistory.from && serviceHistory.from.isValid() ? serviceHistory.from.format(DATE_FORMAT) : undefined,
      to: serviceHistory.to && serviceHistory.to.isValid() ? serviceHistory.to.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((serviceHistory: IServiceHistory) => {
        serviceHistory.from = serviceHistory.from ? moment(serviceHistory.from) : undefined;
        serviceHistory.to = serviceHistory.to ? moment(serviceHistory.to) : undefined;
      });
    }
    return res;
  }
}
