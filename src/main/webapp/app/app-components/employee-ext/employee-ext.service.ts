import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployee } from 'app/shared/model/employee.model';
import {EmployeeService} from "app/entities/employee/employee.service";
import {SessionStorageService} from "ngx-webstorage";

type EntityResponseType = HttpResponse<IEmployee>;
type EntityArrayResponseType = HttpResponse<IEmployee[]>;
type EntityPDFResponseType = HttpResponse<any>;

@Injectable({ providedIn: 'root' })
export class EmployeeExtService extends EmployeeService{
  public resourceUrl = SERVER_API_URL + 'api/employees';
  public resourceUrlExt = SERVER_API_URL + 'api/ext/employees';

  private employeeId = 'employeeExtEmployeeId';

  constructor(protected http: HttpClient, private $sessionStorage: SessionStorageService) {
    super(http);
  }

  storeEmployeeId(employeeId: number):void{
    this.$sessionStorage.store(this.employeeId, employeeId);
  }

  getEmployeeId(): number | null | undefined{
    return this.$sessionStorage.retrieve(this.employeeId);
  }

  clearEmployeeId(): void{
    this.$sessionStorage.clear(this.employeeId);
  }

  downloadIdCard(employeeId: number): Observable<any>{
    return this.http.get(this.resourceUrlExt+'/id-card/'+employeeId, {responseType: 'blob'})
  }
}
