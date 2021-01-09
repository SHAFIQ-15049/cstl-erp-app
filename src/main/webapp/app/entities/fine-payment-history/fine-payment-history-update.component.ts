import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFinePaymentHistory, FinePaymentHistory } from 'app/shared/model/fine-payment-history.model';
import { FinePaymentHistoryService } from './fine-payment-history.service';
import { IFine } from 'app/shared/model/fine.model';
import { FineService } from 'app/entities/fine/fine.service';

@Component({
  selector: 'jhi-fine-payment-history-update',
  templateUrl: './fine-payment-history-update.component.html',
})
export class FinePaymentHistoryUpdateComponent implements OnInit {
  isSaving = false;
  fines: IFine[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    monthType: [],
    amount: [],
    beforeFine: [],
    afterFine: [],
    fine: [],
  });

  constructor(
    protected finePaymentHistoryService: FinePaymentHistoryService,
    protected fineService: FineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finePaymentHistory }) => {
      this.updateForm(finePaymentHistory);

      this.fineService.query().subscribe((res: HttpResponse<IFine[]>) => (this.fines = res.body || []));
    });
  }

  updateForm(finePaymentHistory: IFinePaymentHistory): void {
    this.editForm.patchValue({
      id: finePaymentHistory.id,
      year: finePaymentHistory.year,
      monthType: finePaymentHistory.monthType,
      amount: finePaymentHistory.amount,
      beforeFine: finePaymentHistory.beforeFine,
      afterFine: finePaymentHistory.afterFine,
      fine: finePaymentHistory.fine,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const finePaymentHistory = this.createFromForm();
    if (finePaymentHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.finePaymentHistoryService.update(finePaymentHistory));
    } else {
      this.subscribeToSaveResponse(this.finePaymentHistoryService.create(finePaymentHistory));
    }
  }

  private createFromForm(): IFinePaymentHistory {
    return {
      ...new FinePaymentHistory(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      monthType: this.editForm.get(['monthType'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      beforeFine: this.editForm.get(['beforeFine'])!.value,
      afterFine: this.editForm.get(['afterFine'])!.value,
      fine: this.editForm.get(['fine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinePaymentHistory>>): void {
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

  trackById(index: number, item: IFine): any {
    return item.id;
  }
}
