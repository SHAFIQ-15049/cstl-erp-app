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

type EntityResponseType = HttpResponse<IEmployee>;
type EntityArrayResponseType = HttpResponse<IEmployee[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeExtService extends EmployeeService{
  public resourceUrl = SERVER_API_URL + 'api/employees';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
