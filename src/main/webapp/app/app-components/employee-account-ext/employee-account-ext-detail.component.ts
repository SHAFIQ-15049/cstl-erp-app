import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeAccount } from 'app/shared/model/employee-account.model';
import {EmployeeAccountDetailComponent} from "app/entities/employee-account/employee-account-detail.component";

@Component({
  selector: 'jhi-employee-account-detail',
  templateUrl: './employee-account-ext-detail.component.html',
})
export class EmployeeAccountExtDetailComponent extends EmployeeAccountDetailComponent implements OnInit {

  constructor(protected activatedRoute: ActivatedRoute) {
    super(activatedRoute);
  }
}
