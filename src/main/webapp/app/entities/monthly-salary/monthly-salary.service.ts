import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';

type EntityResponseType = HttpResponse<IMonthlySalary>;
type EntityArrayResponseType = HttpResponse<IMonthlySalary[]>;

@Injectable({ providedIn: 'root' })
export class MonthlySalaryService {
  public resourceUrl = SERVER_API_URL + 'api/monthly-salaries';

  constructor(protected http: HttpClient) {}

  create(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlySalary);
    return this.http
      .post<IMonthlySalary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlySalary);
    return this.http
      .put<IMonthlySalary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMonthlySalary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMonthlySalary[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(monthlySalary: IMonthlySalary): IMonthlySalary {
    const copy: IMonthlySalary = Object.assign({}, monthlySalary, {
      executedOn: monthlySalary.executedOn && monthlySalary.executedOn.isValid() ? monthlySalary.executedOn.toJSON() : undefined,
      executedBy: monthlySalary.executedBy && monthlySalary.executedBy.isValid() ? monthlySalary.executedBy.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.executedOn = res.body.executedOn ? moment(res.body.executedOn) : undefined;
      res.body.executedBy = res.body.executedBy ? moment(res.body.executedBy) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((monthlySalary: IMonthlySalary) => {
        monthlySalary.executedOn = monthlySalary.executedOn ? moment(monthlySalary.executedOn) : undefined;
        monthlySalary.executedBy = monthlySalary.executedBy ? moment(monthlySalary.executedBy) : undefined;
      });
    }
    return res;
  }
}
