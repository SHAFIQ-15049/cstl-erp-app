import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';

type EntityResponseType = HttpResponse<IEmployeeSalary>;
type EntityArrayResponseType = HttpResponse<IEmployeeSalary[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeSalaryService {
  public resourceUrl = SERVER_API_URL + 'api/employee-salaries';

  constructor(protected http: HttpClient) {}

  create(employeeSalary: IEmployeeSalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeSalary);
    return this.http
      .post<IEmployeeSalary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeeSalary: IEmployeeSalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeSalary);
    return this.http
      .put<IEmployeeSalary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeeSalary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeeSalary[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employeeSalary: IEmployeeSalary): IEmployeeSalary {
    const copy: IEmployeeSalary = Object.assign({}, employeeSalary, {
      salaryStartDate:
        employeeSalary.salaryStartDate && employeeSalary.salaryStartDate.isValid() ? employeeSalary.salaryStartDate.toJSON() : undefined,
      salaryEndDate:
        employeeSalary.salaryEndDate && employeeSalary.salaryEndDate.isValid() ? employeeSalary.salaryEndDate.toJSON() : undefined,
      nextIncrementDate:
        employeeSalary.nextIncrementDate && employeeSalary.nextIncrementDate.isValid()
          ? employeeSalary.nextIncrementDate.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.salaryStartDate = res.body.salaryStartDate ? moment(res.body.salaryStartDate) : undefined;
      res.body.salaryEndDate = res.body.salaryEndDate ? moment(res.body.salaryEndDate) : undefined;
      res.body.nextIncrementDate = res.body.nextIncrementDate ? moment(res.body.nextIncrementDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employeeSalary: IEmployeeSalary) => {
        employeeSalary.salaryStartDate = employeeSalary.salaryStartDate ? moment(employeeSalary.salaryStartDate) : undefined;
        employeeSalary.salaryEndDate = employeeSalary.salaryEndDate ? moment(employeeSalary.salaryEndDate) : undefined;
        employeeSalary.nextIncrementDate = employeeSalary.nextIncrementDate ? moment(employeeSalary.nextIncrementDate) : undefined;
      });
    }
    return res;
  }
}
