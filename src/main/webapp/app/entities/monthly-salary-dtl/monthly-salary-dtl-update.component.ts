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
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IMonthlySalary | IEmployee;

@Component({
  selector: 'jhi-monthly-salary-dtl-update',
  templateUrl: './monthly-salary-dtl-update.component.html',
})
export class MonthlySalaryDtlUpdateComponent implements OnInit {
  isSaving = false;
  monthlysalaries: IMonthlySalary[] = [];
  employees: IEmployee[] = [];

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
    status: [],
    executedOn: [],
    executedBy: [],
    note: [],
    monthlySalary: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected monthlySalaryDtlService: MonthlySalaryDtlService,
    protected monthlySalaryService: MonthlySalaryService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlySalaryDtl }) => {
      if (!monthlySalaryDtl.id) {
        const today = moment().startOf('day');
        monthlySalaryDtl.executedOn = today;
        monthlySalaryDtl.executedBy = today;
      }

      this.updateForm(monthlySalaryDtl);

      this.monthlySalaryService.query().subscribe((res: HttpResponse<IMonthlySalary[]>) => (this.monthlysalaries = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
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
      status: monthlySalaryDtl.status,
      executedOn: monthlySalaryDtl.executedOn ? monthlySalaryDtl.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: monthlySalaryDtl.executedBy ? monthlySalaryDtl.executedBy.format(DATE_TIME_FORMAT) : null,
      note: monthlySalaryDtl.note,
      monthlySalary: monthlySalaryDtl.monthlySalary,
      employee: monthlySalaryDtl.employee,
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
      status: this.editForm.get(['status'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value ? moment(this.editForm.get(['executedBy'])!.value, DATE_TIME_FORMAT) : undefined,
      note: this.editForm.get(['note'])!.value,
      monthlySalary: this.editForm.get(['monthlySalary'])!.value,
      employee: this.editForm.get(['employee'])!.value,
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
