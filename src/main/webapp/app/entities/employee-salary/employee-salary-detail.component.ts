import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

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

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected defaultAllowanceService: DefaultAllowanceService) {}

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

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
