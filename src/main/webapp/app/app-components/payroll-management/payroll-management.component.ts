import { Component, OnInit, ViewChild } from '@angular/core';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { combineLatest, merge, Observable, Subject } from 'rxjs';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { Select2Data } from 'ng-select2-component';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { MonthlySalaryDtlService } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl.service';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IMonthlySalary, MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { Moment } from 'moment';
import { PayrollManagementService } from 'app/app-components/payroll-management/payroll-management.service';
import { ActivatedRoute, ActivatedRouteSnapshot, Data, ParamMap, Router } from '@angular/router';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import * as moment from 'moment';

@Component({
  selector: 'jhi-payroll-management',
  templateUrl: './payroll-management.component.html',
  styleUrls: ['./payroll-management.component.scss'],
})
export class PayrollManagementComponent implements OnInit {
  years: number[] = [];
  selectedYear?: number;
  designations: IDesignation[] = [];
  selectedDesignation?: IDesignation;
  selectedDesignationId?: number;
  monthlySalary!: IMonthlySalary;
  monthlySalaryDtls: IMonthlySalaryDtl[] = [];
  selectedMonth?: MonthType;
  fromDate?: Moment;
  toDate?: Moment;
  fromDateStr?: string;
  toDateStr?: string;
  showMonthlySalaryDtl = false;

  constructor(
    private designationService: DesignationService,
    private monthlySalaryService: MonthlySalaryService,
    private monthlySalaryDtlService: MonthlySalaryDtlService,
    private jhiAlertService: JhiAlertService,
    private employeeService: EmployeeService,
    private payrollManagementService: PayrollManagementService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private eventManager: JhiEventManager
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
    this.handleNavigation();
  }

  public designationIdChanged(): void {
    this.designationService.find(this.selectedDesignationId!).subscribe(res => {
      this.selectedDesignation = res.body!;
    });
  }

  private handleNavigation(): void {
    this.activatedRoute.params.subscribe(params => {
      this.selectedYear = +params['selectedYear'];
      this.selectedMonth = params['selectedMonth'];
      this.selectedDesignationId = +params['selectedDesignationId'];
      this.fromDate = params['fromDate'];
      this.toDate = params['toDate'];
      if (this.selectedDesignationId) {
        this.designationService.find(this.selectedDesignationId).subscribe(res => {
          this.selectedDesignation = res.body!;
          this.fetch();
        });
      }
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

  navigate(): void {
    if (this.selectedYear && this.selectedDesignation && this.fromDate && this.toDate) {
      this.selectedDesignationId = this.selectedDesignation.id;
      this.fromDateStr = this.fromDate.toString();
      this.toDateStr = this.toDate.toString();
      this.router.navigate([
        '/payroll-management',
        this.selectedYear,
        this.selectedMonth,
        this.selectedDesignationId,
        this.fromDateStr,
        this.toDateStr,
      ]);
    }
  }

  fetch(): void {
    if (this.selectedYear && this.selectedDesignation && this.selectedMonth) {
      this.monthlySalaryService
        .query({
          'year.equals': this.selectedYear,
          'designationId.equals': this.selectedDesignation.id,
          'month.equals': this.selectedMonth.valueOf(),
        })
        .subscribe(res => {
          if (res.body?.length! > 0) {
            this.monthlySalary = res.body ? res.body[0]! : new MonthlySalary();
            this.showMonthlySalaryDtl = true;
            this.router.navigate(['monthly-salary-dtl'], {
              queryParams: { monthlySalaryId: this.monthlySalary.id },
              relativeTo: this.activatedRoute,
            });
          } else {
            this.showMonthlySalaryDtl = false;
            const monthlySalary = new MonthlySalary();
            monthlySalary.year = this.selectedYear;
            monthlySalary.designation = this.selectedDesignation;
            monthlySalary.month = this.selectedMonth;
            monthlySalary.fromDate = moment(this.fromDate, DATE_TIME_FORMAT);
            monthlySalary.toDate = moment(this.toDate, DATE_TIME_FORMAT);
            this.payrollManagementService.createEmptySalaries(monthlySalary).subscribe(response => {
              this.fetchExistingData();
            });
          }
        });
    } else {
      this.jhiAlertService.error('Year and Designation must be selected');
    }
  }

  generateSalaries(): void {
    this.payrollManagementService.createSalaries(this.monthlySalary).subscribe(res => {
      this.eventManager.broadcast('monthlySalaryDtlListModification');
    });
  }

  regenerateSalaries(): void {
    this.monthlySalary.fromDate = moment(this.fromDate, DATE_TIME_FORMAT);
    this.monthlySalary.toDate = moment(this.toDate, DATE_TIME_FORMAT);
    this.payrollManagementService.regenerateSalaries(this.monthlySalary).subscribe(res => {
      this.eventManager.broadcast('monthlySalaryDtlListModification');
    });
  }

  fetchExistingData(): void {
    this.monthlySalaryService
      .query({
        'year.equals': this.selectedYear,
        'designationId.equals': this.selectedDesignation?.id!,
        'month.equals': this.selectedMonth,
      })
      .subscribe(res => {
        this.monthlySalary = res.body ? res.body[0]! : new MonthlySalary();
        this.showMonthlySalaryDtl = true;
        this.router.navigate(['monthly-salary-dtl'], {
          queryParams: { monthlySalaryId: this.monthlySalary.id },
          relativeTo: this.activatedRoute,
        });
      });
  }
}
