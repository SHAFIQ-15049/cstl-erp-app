import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeAccount } from 'app/shared/model/employee-account.model';

@Component({
  selector: 'jhi-employee-account-detail',
  templateUrl: './employee-account-detail.component.html',
})
export class EmployeeAccountDetailComponent implements OnInit {
  employeeAccount: IEmployeeAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeAccount }) => (this.employeeAccount = employeeAccount));
  }

  previousState(): void {
    window.history.back();
  }
}
