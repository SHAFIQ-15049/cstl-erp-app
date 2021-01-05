import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdvancePaymentHistory, AdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';
import { AdvancePaymentHistoryService } from './advance-payment-history.service';
import { IAdvance } from 'app/shared/model/advance.model';
import { AdvanceService } from 'app/entities/advance/advance.service';

@Component({
  selector: 'jhi-advance-payment-history-update',
  templateUrl: './advance-payment-history-update.component.html',
})
export class AdvancePaymentHistoryUpdateComponent implements OnInit {
  isSaving = false;
  advances: IAdvance[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    monthType: [],
    amount: [],
    before: [],
    after: [],
    advance: [],
  });

  constructor(
    protected advancePaymentHistoryService: AdvancePaymentHistoryService,
    protected advanceService: AdvanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advancePaymentHistory }) => {
      this.updateForm(advancePaymentHistory);

      this.advanceService.query().subscribe((res: HttpResponse<IAdvance[]>) => (this.advances = res.body || []));
    });
  }

  updateForm(advancePaymentHistory: IAdvancePaymentHistory): void {
    this.editForm.patchValue({
      id: advancePaymentHistory.id,
      year: advancePaymentHistory.year,
      monthType: advancePaymentHistory.monthType,
      amount: advancePaymentHistory.amount,
      before: advancePaymentHistory.before,
      after: advancePaymentHistory.after,
      advance: advancePaymentHistory.advance,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const advancePaymentHistory = this.createFromForm();
    if (advancePaymentHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.advancePaymentHistoryService.update(advancePaymentHistory));
    } else {
      this.subscribeToSaveResponse(this.advancePaymentHistoryService.create(advancePaymentHistory));
    }
  }

  private createFromForm(): IAdvancePaymentHistory {
    return {
      ...new AdvancePaymentHistory(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      monthType: this.editForm.get(['monthType'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      before: this.editForm.get(['before'])!.value,
      after: this.editForm.get(['after'])!.value,
      advance: this.editForm.get(['advance'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvancePaymentHistory>>): void {
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

  trackById(index: number, item: IAdvance): any {
    return item.id;
  }
}
