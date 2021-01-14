import { Component, OnInit, ViewChild } from '@angular/core';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { merge, Observable, Subject } from 'rxjs';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { Select2Data } from 'ng-select2-component';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { MonthlySalaryDtlService } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl.service';
import { JhiAlertService } from 'ng-jhipster';
import { IMonthlySalary, MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import {Moment} from "moment";
import {PayrollManagementService} from "app/app-components/payroll-management/payroll-management.service";

@Component({
  selector: 'jhi-payroll-management',
  templateUrl: './payroll-management.component.html',
  styleUrls: ['./payroll-management.component.scss'],
})
export class PayrollManagementComponent implements OnInit {
  years: number[] = [];
  selectedYear?: number;
  designations: IDesignation[] = [];
  designationSelectData: Select2Data = [];
  selectedDesignation?: IDesignation;
  monthlySalary!: IMonthlySalary;
  monthlySalaryDtls: IMonthlySalaryDtl[] = [];
  selectedMonth?: MonthType;
  fromDate?: Moment;
  toDate?: Moment;

  constructor(
    private designationService: DesignationService,
    private monthlySalaryService: MonthlySalaryService,
    private monthlySalaryDtlService: MonthlySalaryDtlService,
    private jhiAlertService: JhiAlertService,
    private employeeService: EmployeeService,
    private payrollManagementService: PayrollManagementService
  ) {}

  ngOnInit(): void {
    this.configureYears();
    this.designationService
      .query({
        size: 2000,
      })
      .subscribe(res => {
        this.designations = res.body!;
      });
  }

  configureYears(): void {
    let year = new Date().getFullYear();
    this.selectedYear = new Date().getFullYear();
    this.years.push(year);
    for (let i = 0; i < 3; i++) {
      year -= 1;
      this.years.push(year);
    }
  }

  fetch(): void {
    if (this.selectedYear && this.selectedDesignation && this.fromDate && this.toDate) {
      this.monthlySalaryService
        .query({
          'year.equals': this.selectedYear,
          'designationId.equals': this.selectedDesignation.id,
        }).subscribe((res)=>{
        if (res.body?.length! > 0) {
          this.monthlySalary = res.body ? res.body[0]! : new MonthlySalary();
          this.fetchMonthlySalaryDtl();
        } else{
          this.payrollManagementService.createEmptySalaries(this.selectedYear!, this.selectedMonth!, this.selectedDesignation?.id!).subscribe((response)=>{
            this.fetch();
          });
        }
      });
    } else {
      this.jhiAlertService.error('Year and Designation must be selected');
    }
  }

  fetchMonthlySalaryDtl(): void {
    if (this.monthlySalary) {
      this.monthlySalaryDtlService
        .query({
          'monthlySalaryId.equals': this.monthlySalary?.id,
        })
        .subscribe(res => {
          this.monthlySalaryDtls = res.body!;
        });
    } else {
      this.jhiAlertService.error('Monthly salary is not configured by the system');
    }
  }
}
