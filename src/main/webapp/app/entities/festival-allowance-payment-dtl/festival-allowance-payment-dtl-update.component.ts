import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFestivalAllowancePaymentDtl, FestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';
import { FestivalAllowancePaymentDtlService } from './festival-allowance-payment-dtl.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';

type SelectableEntity = IEmployee | IFestivalAllowancePayment;

@Component({
  selector: 'jhi-festival-allowance-payment-dtl-update',
  templateUrl: './festival-allowance-payment-dtl-update.component.html',
})
export class FestivalAllowancePaymentDtlUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  festivalallowancepayments: IFestivalAllowancePayment[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    status: [],
    executedOn: [],
    executedBy: [],
    note: [],
    employee: [],
    festivalAllowancePayment: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected festivalAllowancePaymentDtlService: FestivalAllowancePaymentDtlService,
    protected employeeService: EmployeeService,
    protected festivalAllowancePaymentService: FestivalAllowancePaymentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivalAllowancePaymentDtl }) => {
      if (!festivalAllowancePaymentDtl.id) {
        const today = moment().startOf('day');
        festivalAllowancePaymentDtl.executedOn = today;
      }

      this.updateForm(festivalAllowancePaymentDtl);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.festivalAllowancePaymentService
        .query()
        .subscribe((res: HttpResponse<IFestivalAllowancePayment[]>) => (this.festivalallowancepayments = res.body || []));
    });
  }

  updateForm(festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl): void {
    this.editForm.patchValue({
      id: festivalAllowancePaymentDtl.id,
      amount: festivalAllowancePaymentDtl.amount,
      status: festivalAllowancePaymentDtl.status,
      executedOn: festivalAllowancePaymentDtl.executedOn ? festivalAllowancePaymentDtl.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: festivalAllowancePaymentDtl.executedBy,
      note: festivalAllowancePaymentDtl.note,
      employee: festivalAllowancePaymentDtl.employee,
      festivalAllowancePayment: festivalAllowancePaymentDtl.festivalAllowancePayment,
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
    const festivalAllowancePaymentDtl = this.createFromForm();
    if (festivalAllowancePaymentDtl.id !== undefined) {
      this.subscribeToSaveResponse(this.festivalAllowancePaymentDtlService.update(festivalAllowancePaymentDtl));
    } else {
      this.subscribeToSaveResponse(this.festivalAllowancePaymentDtlService.create(festivalAllowancePaymentDtl));
    }
  }

  private createFromForm(): IFestivalAllowancePaymentDtl {
    return {
      ...new FestivalAllowancePaymentDtl(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      status: this.editForm.get(['status'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value,
      note: this.editForm.get(['note'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      festivalAllowancePayment: this.editForm.get(['festivalAllowancePayment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFestivalAllowancePaymentDtl>>): void {
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
