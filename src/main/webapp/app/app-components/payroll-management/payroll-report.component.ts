import { Component, OnInit } from '@angular/core';
import { IDesignation } from 'app/shared/model/designation.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { MonthlySalaryDtlService } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl.service';
import { DepartmentService } from 'app/entities/department/department.service';
import { JhiAlertService } from 'ng-jhipster';
import { DesignationService } from 'app/entities/designation/designation.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'jhi-payroll-report',
  templateUrl: './payroll-report.component.html',
  styleUrls: ['./payroll-report.component.scss'],
})
export class PayrollReportComponent implements OnInit {
  years: number[] = [];
  selectedYear?: number;
  selectedMonth?: MonthType;
  departments: IDesignation[] = [];
  selectedDepartmentId?: number;
  designations: IDesignation[] = [];
  selectedDesignationId?: number;

  constructor(
    private monthlySalaryDtlService: MonthlySalaryDtlService,
    private departmentService: DepartmentService,
    private jhiAlertService: JhiAlertService,
    private designationService: DesignationService
  ) {}

  ngOnInit(): void {
    this.configureYears();
    forkJoin(this.departmentService.query({ size: 1000 }), this.designationService.query({ size: 1000 })).subscribe(res => {
      this.departments = res[0].body ? res[0].body : [];
      this.designations = res[1].body ? res[1].body : [];
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
}
