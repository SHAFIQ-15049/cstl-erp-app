import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { DefaultAllowanceService } from 'app/entities/default-allowance/default-allowance.service';
import { DefaultAllowance, IDefaultAllowance } from 'app/shared/model/default-allowance.model';
import { ActiveStatus } from 'app/shared/model/enumerations/active-status.model';

@Component({
  selector: 'jhi-employee-salary-detail',
  templateUrl: './employee-salary-detail.component.html',
})
export class EmployeeSalaryDetailComponent implements OnInit {
  employeeSalary: IEmployeeSalary | null = null;
  defaultAllowance?: IDefaultAllowance;

  constructor(protected activatedRoute: ActivatedRoute, protected defaultAllowanceService: DefaultAllowanceService) {}

  ngOnInit(): void {
    this.defaultAllowanceService
      .query({
        'status.equals': ActiveStatus.ACTIVE,
      })
      .subscribe(res => {
        this.defaultAllowance = res.body ? res.body[0]! : new DefaultAllowance();
      });
    this.activatedRoute.data.subscribe(({ employeeSalary }) => (this.employeeSalary = employeeSalary));
  }

  previousState(): void {
    window.history.back();
  }
}
