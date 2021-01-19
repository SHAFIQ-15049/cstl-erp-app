import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IOverTime, OverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from './over-time.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IDesignation | IEmployee;

@Component({
  selector: 'jhi-over-time-update',
  templateUrl: './over-time-update.component.html',
})
export class OverTimeUpdateComponent implements OnInit {
  isSaving = false;
  designations: IDesignation[] = [];
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
    fromDate: [],
    toDate: [],
    totalOverTime: [],
    officialOverTime: [],
    extraOverTime: [],
    totalAmount: [],
    officialAmount: [],
    extraAmount: [],
    note: [],
    executedOn: [],
    executedBy: [],
    designation: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected overTimeService: OverTimeService,
    protected designationService: DesignationService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ overTime }) => {
      if (!overTime.id) {
        const today = moment().startOf('day');
        overTime.fromDate = today;
        overTime.toDate = today;
        overTime.executedOn = today;
      }

      this.updateForm(overTime);

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(overTime: IOverTime): void {
    this.editForm.patchValue({
      id: overTime.id,
      year: overTime.year,
      month: overTime.month,
      fromDate: overTime.fromDate ? overTime.fromDate.format(DATE_TIME_FORMAT) : null,
      toDate: overTime.toDate ? overTime.toDate.format(DATE_TIME_FORMAT) : null,
      totalOverTime: overTime.totalOverTime,
      officialOverTime: overTime.officialOverTime,
      extraOverTime: overTime.extraOverTime,
      totalAmount: overTime.totalAmount,
      officialAmount: overTime.officialAmount,
      extraAmount: overTime.extraAmount,
      note: overTime.note,
      executedOn: overTime.executedOn ? overTime.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: overTime.executedBy,
      designation: overTime.designation,
      employee: overTime.employee,
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
    const overTime = this.createFromForm();
    if (overTime.id !== undefined) {
      this.subscribeToSaveResponse(this.overTimeService.update(overTime));
    } else {
      this.subscribeToSaveResponse(this.overTimeService.create(overTime));
    }
  }

  private createFromForm(): IOverTime {
    return {
      ...new OverTime(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      toDate: this.editForm.get(['toDate'])!.value ? moment(this.editForm.get(['toDate'])!.value, DATE_TIME_FORMAT) : undefined,
      totalOverTime: this.editForm.get(['totalOverTime'])!.value,
      officialOverTime: this.editForm.get(['officialOverTime'])!.value,
      extraOverTime: this.editForm.get(['extraOverTime'])!.value,
      totalAmount: this.editForm.get(['totalAmount'])!.value,
      officialAmount: this.editForm.get(['officialAmount'])!.value,
      extraAmount: this.editForm.get(['extraAmount'])!.value,
      note: this.editForm.get(['note'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOverTime>>): void {
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
