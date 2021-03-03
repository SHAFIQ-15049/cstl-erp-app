import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployeeSalary, EmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { DefaultAllowanceService } from 'app/entities/default-allowance/default-allowance.service';
import { DefaultAllowance, IDefaultAllowance } from 'app/shared/model/default-allowance.model';
import { ActiveStatus } from 'app/shared/model/enumerations/active-status.model';

@Component({
  selector: 'jhi-employee-salary-update',
  templateUrl: './employee-salary-update.component.html',
})
export class EmployeeSalaryUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  salaryStartDateDp: any;
  salaryEndDateDp: any;
  nextIncrementDateDp: any;
  defaultAllowance?: IDefaultAllowance;

  editForm = this.fb.group({
    id: [],
    gross: [null, [Validators.required]],
    incrementAmount: [null, [Validators.required]],
    incrementPercentage: [],
    salaryStartDate: [null, [Validators.required]],
    salaryEndDate: [null, [Validators.required]],
    nextIncrementDate: [],
    basic: [null, [Validators.required]],
    basicPercent: [],
    houseRent: [null, [Validators.required]],
    houseRentPercent: [],
    totalAllowance: [],
    medicalAllowance: [],
    medicalAllowancePercent: [],
    convinceAllowance: [],
    convinceAllowancePercent: [],
    foodAllowance: [],
    foodAllowancePercent: [],
    specialAllowanceActiveStatus: [],
    specialAllowance: [],
    specialAllowancePercent: [],
    specialAllowanceDescription: [],
    insuranceActiveStatus: [],
    insuranceAmount: [],
    insurancePercent: [],
    insuranceDescription: [],
    insuranceProcessType: [],
    status: [null, [Validators.required]],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeSalaryService: EmployeeSalaryService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private defaultAllowanceService: DefaultAllowanceService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeSalary }) => {
      this.updateForm(employeeSalary);
      this.defaultAllowanceService
        .query({
          'status.equals  ': ActiveStatus.ACTIVE,
        })
        .subscribe(res => {
          this.defaultAllowance = res.body && res.body?.length > 0 ? res.body[0] : new DefaultAllowance();
        });
      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(employeeSalary: IEmployeeSalary): void {
    this.editForm.patchValue({
      id: employeeSalary.id,
      gross: employeeSalary.gross,
      incrementAmount: employeeSalary.incrementAmount,
      incrementPercentage: employeeSalary.incrementPercentage,
      salaryStartDate: employeeSalary.salaryStartDate ? employeeSalary.salaryStartDate.format(DATE_TIME_FORMAT) : null,
      salaryEndDate: employeeSalary.salaryEndDate ? employeeSalary.salaryEndDate.format(DATE_TIME_FORMAT) : null,
      nextIncrementDate: employeeSalary.nextIncrementDate ? employeeSalary.nextIncrementDate.format(DATE_TIME_FORMAT) : null,
      basic: employeeSalary.basic,
      basicPercent: employeeSalary.basicPercent,
      houseRent: employeeSalary.houseRent,
      houseRentPercent: employeeSalary.houseRentPercent,
      totalAllowance: employeeSalary.totalAllowance,
      medicalAllowance: employeeSalary.medicalAllowance,
      medicalAllowancePercent: employeeSalary.medicalAllowancePercent,
      convinceAllowance: employeeSalary.convinceAllowance,
      convinceAllowancePercent: employeeSalary.convinceAllowancePercent,
      foodAllowance: employeeSalary.foodAllowance,
      foodAllowancePercent: employeeSalary.foodAllowancePercent,
      specialAllowanceActiveStatus: employeeSalary.specialAllowanceActiveStatus,
      specialAllowance: employeeSalary.specialAllowance,
      specialAllowancePercent: employeeSalary.specialAllowancePercent,
      specialAllowanceDescription: employeeSalary.specialAllowanceDescription,
      insuranceActiveStatus: employeeSalary.insuranceActiveStatus,
      insuranceAmount: employeeSalary.insuranceAmount,
      insurancePercent: employeeSalary.insurancePercent,
      insuranceDescription: employeeSalary.insuranceDescription,
      insuranceProcessType: employeeSalary.insuranceProcessType,
      status: employeeSalary.status,
      employee: employeeSalary.employee,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('codeNodeErpApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeSalary = this.createFromForm();
    if (employeeSalary.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeSalaryService.update(employeeSalary));
    } else {
      this.subscribeToSaveResponse(this.employeeSalaryService.create(employeeSalary));
    }
  }

  private createFromForm(): IEmployeeSalary {
    return {
      ...new EmployeeSalary(),
      id: this.editForm.get(['id'])!.value,
      gross: this.editForm.get(['gross'])!.value,
      incrementAmount: this.editForm.get(['incrementAmount'])!.value,
      incrementPercentage: this.editForm.get(['incrementPercentage'])!.value,
      salaryStartDate: this.editForm.get(['salaryStartDate'])!.value
        ? moment(this.editForm.get(['salaryStartDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      salaryEndDate: this.editForm.get(['salaryEndDate'])!.value
        ? moment(this.editForm.get(['salaryEndDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      nextIncrementDate: this.editForm.get(['nextIncrementDate'])!.value
        ? moment(this.editForm.get(['nextIncrementDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      basic: this.editForm.get(['basic'])!.value,
      basicPercent: this.editForm.get(['basicPercent'])!.value,
      houseRent: this.editForm.get(['houseRent'])!.value,
      houseRentPercent: this.editForm.get(['houseRentPercent'])!.value,
      totalAllowance: this.editForm.get(['totalAllowance'])!.value,
      medicalAllowance: this.editForm.get(['medicalAllowance'])!.value,
      medicalAllowancePercent: this.editForm.get(['medicalAllowancePercent'])!.value,
      convinceAllowance: this.editForm.get(['convinceAllowance'])!.value,
      convinceAllowancePercent: this.editForm.get(['convinceAllowancePercent'])!.value,
      foodAllowance: this.editForm.get(['foodAllowance'])!.value,
      foodAllowancePercent: this.editForm.get(['foodAllowancePercent'])!.value,
      specialAllowanceActiveStatus: this.editForm.get(['specialAllowanceActiveStatus'])!.value,
      specialAllowance: this.editForm.get(['specialAllowance'])!.value,
      specialAllowancePercent: this.editForm.get(['specialAllowancePercent'])!.value,
      specialAllowanceDescription: this.editForm.get(['specialAllowanceDescription'])!.value,
      insuranceActiveStatus: this.editForm.get(['insuranceActiveStatus'])!.value,
      insuranceAmount: this.editForm.get(['insuranceAmount'])!.value,
      insurancePercent: this.editForm.get(['insurancePercent'])!.value,
      insuranceDescription: this.editForm.get(['insuranceDescription'])!.value,
      insuranceProcessType: this.editForm.get(['insuranceProcessType'])!.value,
      status: this.editForm.get(['status'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSalary>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }

  extractGross(): void {
    const gross = this.editForm.get('gross')?.value;
    const medicalAllowance = this.editForm.get('medicalAllowance')?.value || this.defaultAllowance?.medicalAllowance;
    const convinceAllowance = this.editForm.get('convinceAllowance')?.value || this.defaultAllowance?.convinceAllowance;
    const foodAllowance = this.editForm.get('foodAllowance')?.value || this.defaultAllowance?.foodAllowance;

    const basic = ((gross - (medicalAllowance + convinceAllowance + foodAllowance)) / 1.5).toFixed(3);
    const basicPercent = ((+basic / gross) * 100).toFixed(3);
    const houseRent = (+basic / 2).toFixed(3);
    const houseRentPercent = ((+houseRent / gross) * 100).toFixed(3);

    this.editForm.get('basic')?.setValue(basic);
    this.editForm.get('basicPercent')?.setValue(basicPercent);
    this.editForm.get('houseRent')?.setValue(houseRent);
    this.editForm.get('houseRentPercent')?.setValue(houseRentPercent);
  }
}
