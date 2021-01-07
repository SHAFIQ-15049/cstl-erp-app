import { Injectable } from '@angular/core';
import {SERVER_API_URL} from "app/app.constants";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {MonthType} from "app/shared/model/enumerations/month-type.model";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PayrollManagementService {
  public resourceUrl = SERVER_API_URL+'api/payroll-management'

  constructor(private http: HttpClient) { }

  createEmptySalaries(year:number, month: MonthType, designationId: number): Observable<HttpResponse<string>>{
    return this.http
      .get<string>(`${this.resourceUrl}/generate-empty-salaries/year/${year}/month/${month}/designation/${designationId}`, {observe: "response"})
      .pipe((res)=> res);
  }
}
