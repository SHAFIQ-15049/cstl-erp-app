import { Component, OnInit } from '@angular/core';
import { IDesignation } from 'app/shared/model/designation.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { MonthlySalaryDtlService } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl.service';
import { DepartmentService } from 'app/entities/department/department.service';
import { JhiAlertService } from 'ng-jhipster';
import { DesignationService } from 'app/entities/designation/designation.service';
import { forkJoin } from 'rxjs';
import { PayrollManagementService } from 'app/app-components/payroll-management/payroll-management.service';

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
    private designationService: DesignationService,
    private payrollManagementService: PayrollManagementService
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

  downloadReport(): void {
    this.payrollManagementService
      .downloadReport(this.selectedYear!, this.selectedMonth!, this.selectedDepartmentId!, this.selectedDesignationId!)
      .subscribe(data => {
        const blob: any = new Blob([data], { type: 'application/octet-stream' });
        const downloadURL = window.URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = downloadURL;
        link.download = 'salary-report.xls';
        link.click();
      });
  }
}
