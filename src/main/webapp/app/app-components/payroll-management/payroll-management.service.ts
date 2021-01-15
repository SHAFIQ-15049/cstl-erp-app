import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { map } from 'rxjs/operators';
import {IMonthlySalary} from "app/shared/model/monthly-salary.model";
import {DATE_FORMAT} from "app/shared/constants/input.constants";
import * as moment from "moment";


type EntityResponseType = HttpResponse<IMonthlySalary>;
type EntityArrayResponseType = HttpResponse<IMonthlySalary[]>;

@Injectable({
  providedIn: 'root',
})
export class PayrollManagementService {
  public resourceUrl = SERVER_API_URL + 'api/payroll-management';

  constructor(private http: HttpClient) {}


  createEmptySalaries(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monthlySalary);
    return this.http
      .post<IMonthlySalary>(`${this.resourceUrl}/generate-empty-salaries`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
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
