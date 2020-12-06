import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeAccount } from 'app/shared/model/employee-account.model';
import {EmployeeAccountService} from "app/entities/employee-account/employee-account.service";

type EntityResponseType = HttpResponse<IEmployeeAccount>;
type EntityArrayResponseType = HttpResponse<IEmployeeAccount[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeAccountExtService extends EmployeeAccountService{
  public resourceUrl = SERVER_API_URL + 'api/employee-accounts';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
