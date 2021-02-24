import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMonthlySalaryDtl, MonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { MonthlySalaryDtlService } from './monthly-salary-dtl.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';

type SelectableEntity = IEmployee | IMonthlySalary;

@Component({
  selector: 'jhi-monthly-salary-dtl-update',
  templateUrl: './monthly-salary-dtl-update.component.html',
})
export class MonthlySalaryDtlUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  monthlysalaries: IMonthlySalary[] = [];

  editForm = this.fb.group({
    id: [],
    gross: [],
    basic: [],
    basicPercent: [],
    houseRent: [],
    houseRentPercent: [],
    medicalAllowance: [],
    medicalAllowancePercent: [],
    convinceAllowance: [],
    convinceAllowancePercent: [],
    foodAllowance: [],
    foodAllowancePercent: [],
    fine: [],
    advance: [],
    totalWorkingDays: [],
    regularLeave: [],
    sickLeave: [],
    compensationLeave: [],
    festivalLeave: [],
    weeklyLeave: [],
    present: [],
    absent: [],
    totalMonthDays: [],
    overTimeHour: [],
    overTimeSalaryHourly: [],
    overTimeSalary: [],
    presentBonus: [],
    absentFine: [],
    stampPrice: [],
    tax: [],
    others: [],
    totalPayable: [],
    status: [],
    type: [],
    executedOn: [],
    executedBy: [],
    note: [],
    employee: [],
    monthlySalary: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected monthlySalaryDtlService: MonthlySalaryDtlService,
    protected employeeService: EmployeeService,
    protected monthlySalaryService: MonthlySalaryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlySalaryDtl }) => {
      if (!monthlySalaryDtl.id) {
        const today = moment().startOf('day');
        monthlySalaryDtl.executedOn = today;
      }

      this.updateForm(monthlySalaryDtl);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.monthlySalaryService.query().subscribe((res: HttpResponse<IMonthlySalary[]>) => (this.monthlysalaries = res.body || []));
    });
  }

  updateForm(monthlySalaryDtl: IMonthlySalaryDtl): void {
    this.editForm.patchValue({
      id: monthlySalaryDtl.id,
      gross: monthlySalaryDtl.gross,
      basic: monthlySalaryDtl.basic,
      basicPercent: monthlySalaryDtl.basicPercent,
      houseRent: monthlySalaryDtl.houseRent,
      houseRentPercent: monthlySalaryDtl.houseRentPercent,
      medicalAllowance: monthlySalaryDtl.medicalAllowance,
      medicalAllowancePercent: monthlySalaryDtl.medicalAllowancePercent,
      convinceAllowance: monthlySalaryDtl.convinceAllowance,
      convinceAllowancePercent: monthlySalaryDtl.convinceAllowancePercent,
      foodAllowance: monthlySalaryDtl.foodAllowance,
      foodAllowancePercent: monthlySalaryDtl.foodAllowancePercent,
      fine: monthlySalaryDtl.fine,
      advance: monthlySalaryDtl.advance,
      totalWorkingDays: monthlySalaryDtl.totalWorkingDays,
      regularLeave: monthlySalaryDtl.regularLeave,
      sickLeave: monthlySalaryDtl.sickLeave,
      compensationLeave: monthlySalaryDtl.compensationLeave,
      festivalLeave: monthlySalaryDtl.festivalLeave,
      weeklyLeave: monthlySalaryDtl.weeklyLeave,
      present: monthlySalaryDtl.present,
      absent: monthlySalaryDtl.absent,
      totalMonthDays: monthlySalaryDtl.totalMonthDays,
      overTimeHour: monthlySalaryDtl.overTimeHour,
      overTimeSalaryHourly: monthlySalaryDtl.overTimeSalaryHourly,
      overTimeSalary: monthlySalaryDtl.overTimeSalary,
      presentBonus: monthlySalaryDtl.presentBonus,
      absentFine: monthlySalaryDtl.absentFine,
      stampPrice: monthlySalaryDtl.stampPrice,
      tax: monthlySalaryDtl.tax,
      others: monthlySalaryDtl.others,
      totalPayable: monthlySalaryDtl.totalPayable,
      status: monthlySalaryDtl.status,
      type: monthlySalaryDtl.type,
      executedOn: monthlySalaryDtl.executedOn ? monthlySalaryDtl.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: monthlySalaryDtl.executedBy,
      note: monthlySalaryDtl.note,
      employee: monthlySalaryDtl.employee,
      monthlySalary: monthlySalaryDtl.monthlySalary,
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
    const monthlySalaryDtl = this.createFromForm();
    if (monthlySalaryDtl.id !== undefined) {
      this.subscribeToSaveResponse(this.monthlySalaryDtlService.update(monthlySalaryDtl));
    } else {
      this.subscribeToSaveResponse(this.monthlySalaryDtlService.create(monthlySalaryDtl));
    }
  }

  private createFromForm(): IMonthlySalaryDtl {
    return {
      ...new MonthlySalaryDtl(),
      id: this.editForm.get(['id'])!.value,
      gross: this.editForm.get(['gross'])!.value,
      basic: this.editForm.get(['basic'])!.value,
      basicPercent: this.editForm.get(['basicPercent'])!.value,
      houseRent: this.editForm.get(['houseRent'])!.value,
      houseRentPercent: this.editForm.get(['houseRentPercent'])!.value,
      medicalAllowance: this.editForm.get(['medicalAllowance'])!.value,
      medicalAllowancePercent: this.editForm.get(['medicalAllowancePercent'])!.value,
      convinceAllowance: this.editForm.get(['convinceAllowance'])!.value,
      convinceAllowancePercent: this.editForm.get(['convinceAllowancePercent'])!.value,
      foodAllowance: this.editForm.get(['foodAllowance'])!.value,
      foodAllowancePercent: this.editForm.get(['foodAllowancePercent'])!.value,
      fine: this.editForm.get(['fine'])!.value,
      advance: this.editForm.get(['advance'])!.value,
      totalWorkingDays: this.editForm.get(['totalWorkingDays'])!.value,
      regularLeave: this.editForm.get(['regularLeave'])!.value,
      sickLeave: this.editForm.get(['sickLeave'])!.value,
      compensationLeave: this.editForm.get(['compensationLeave'])!.value,
      festivalLeave: this.editForm.get(['festivalLeave'])!.value,
      weeklyLeave: this.editForm.get(['weeklyLeave'])!.value,
      present: this.editForm.get(['present'])!.value,
      absent: this.editForm.get(['absent'])!.value,
      totalMonthDays: this.editForm.get(['totalMonthDays'])!.value,
      overTimeHour: this.editForm.get(['overTimeHour'])!.value,
      overTimeSalaryHourly: this.editForm.get(['overTimeSalaryHourly'])!.value,
      overTimeSalary: this.editForm.get(['overTimeSalary'])!.value,
      presentBonus: this.editForm.get(['presentBonus'])!.value,
      absentFine: this.editForm.get(['absentFine'])!.value,
      stampPrice: this.editForm.get(['stampPrice'])!.value,
      tax: this.editForm.get(['tax'])!.value,
      others: this.editForm.get(['others'])!.value,
      totalPayable: this.editForm.get(['totalPayable'])!.value,
      status: this.editForm.get(['status'])!.value,
      type: this.editForm.get(['type'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value,
      note: this.editForm.get(['note'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      monthlySalary: this.editForm.get(['monthlySalary'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlySalaryDtl>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
