import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';

type EntityResponseType = HttpResponse<IMonthlySalaryDtl>;
type EntityArrayResponseType = HttpResponse<IMonthlySalaryDtl[]>;

@Injectable({ providedIn: 'root' })
export class MonthlySalaryDtlService {
  public resourceUrl = SERVER_API_URL + 'api/monthly-salary-dtls';

  constructor(protected http: HttpClient) {}

  create(monthlySalaryDtl: IMonthlySalaryDtl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlySalaryDtl);
    return this.http
      .post<IMonthlySalaryDtl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(monthlySalaryDtl: IMonthlySalaryDtl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlySalaryDtl);
    return this.http
      .put<IMonthlySalaryDtl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMonthlySalaryDtl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMonthlySalaryDtl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  public convertDateFromClient(monthlySalaryDtl: IMonthlySalaryDtl): IMonthlySalaryDtl {
    const copy: IMonthlySalaryDtl = Object.assign({}, monthlySalaryDtl, {
      executedOn: monthlySalaryDtl.executedOn && monthlySalaryDtl.executedOn.isValid() ? monthlySalaryDtl.executedOn.toJSON() : undefined,
    });
    return copy;
  }

  public convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.executedOn = res.body.executedOn ? moment(res.body.executedOn) : undefined;
    }
    return res;
  }

  public convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((monthlySalaryDtl: IMonthlySalaryDtl) => {
        monthlySalaryDtl.executedOn = monthlySalaryDtl.executedOn ? moment(monthlySalaryDtl.executedOn) : undefined;
      });
    }
    return res;
  }
}
