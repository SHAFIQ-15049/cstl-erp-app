import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFestivalAllowancePayment, FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { FestivalAllowancePaymentService } from './festival-allowance-payment.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-festival-allowance-payment-update',
  templateUrl: './festival-allowance-payment-update.component.html',
})
export class FestivalAllowancePaymentUpdateComponent implements OnInit {
  isSaving = false;
  designations: IDesignation[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
    status: [],
    executedOn: [],
    executedBy: [],
    designation: [],
  });

  constructor(
    protected festivalAllowancePaymentService: FestivalAllowancePaymentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivalAllowancePayment }) => {
      if (!festivalAllowancePayment.id) {
        const today = moment().startOf('day');
        festivalAllowancePayment.executedOn = today;
      }

      this.updateForm(festivalAllowancePayment);

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    });
  }

  updateForm(festivalAllowancePayment: IFestivalAllowancePayment): void {
    this.editForm.patchValue({
      id: festivalAllowancePayment.id,
      year: festivalAllowancePayment.year,
      month: festivalAllowancePayment.month,
      status: festivalAllowancePayment.status,
      executedOn: festivalAllowancePayment.executedOn ? festivalAllowancePayment.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: festivalAllowancePayment.executedBy,
      designation: festivalAllowancePayment.designation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const festivalAllowancePayment = this.createFromForm();
    if (festivalAllowancePayment.id !== undefined) {
      this.subscribeToSaveResponse(this.festivalAllowancePaymentService.regenerate(festivalAllowancePayment));
    } else {
      this.subscribeToSaveResponse(this.festivalAllowancePaymentService.create(festivalAllowancePayment));
    }
  }

  private createFromForm(): IFestivalAllowancePayment {
    return {
      ...new FestivalAllowancePayment(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      status: this.editForm.get(['status'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value,
      designation: this.editForm.get(['designation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFestivalAllowancePayment>>): void {
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

  trackById(index: number, item: IDesignation): any {
    return item.id;
  }
}
