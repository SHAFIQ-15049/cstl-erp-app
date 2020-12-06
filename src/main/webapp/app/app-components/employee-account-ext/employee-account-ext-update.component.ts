import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployeeAccount, EmployeeAccount } from 'app/shared/model/employee-account.model';
import { EmployeeAccountExtService } from './employee-account-ext.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import {EmployeeAccountUpdateComponent} from "app/entities/employee-account/employee-account-update.component";

@Component({
  selector: 'jhi-employee-account-update',
  templateUrl: './employee-account-ext-update.component.html',
})
export class EmployeeAccountExtUpdateComponent extends EmployeeAccountUpdateComponent implements OnInit {


  constructor(
    protected employeeAccountService: EmployeeAccountExtService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(employeeAccountService, employeeService, activatedRoute, fb);
  }


}
