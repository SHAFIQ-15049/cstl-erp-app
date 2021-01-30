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

  downloadSalaryReport(salaryId: number): Observable<any> {
    return this.http.get(this.resourceUrl + '/report/' + salaryId, { responseType: 'blob' });
  }

  protected convertDateFromClient(monthlySalary: IMonthlySalary): IMonthlySalary {
    const copy: IMonthlySalary = Object.assign({}, monthlySalary, {
      fromDate: monthlySalary.fromDate && monthlySalary.fromDate.isValid() ? monthlySalary.fromDate.toJSON() : undefined,
      toDate: monthlySalary.toDate && monthlySalary.toDate.isValid() ? monthlySalary.toDate.toJSON() : undefined,
      executedOn: monthlySalary.executedOn && monthlySalary.executedOn.isValid() ? monthlySalary.executedOn.toJSON() : undefined,
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
      res.body.forEach((monthlySalary: IMonthlySalary) => {
        monthlySalary.fromDate = monthlySalary.fromDate ? moment(monthlySalary.fromDate) : undefined;
        monthlySalary.toDate = monthlySalary.toDate ? moment(monthlySalary.toDate) : undefined;
        monthlySalary.executedOn = monthlySalary.executedOn ? moment(monthlySalary.executedOn) : undefined;
      });
    }
    return res;
  }
}
