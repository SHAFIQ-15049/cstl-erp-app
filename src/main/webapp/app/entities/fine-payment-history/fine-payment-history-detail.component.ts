import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinePaymentHistory } from 'app/shared/model/fine-payment-history.model';

@Component({
  selector: 'jhi-fine-payment-history-detail',
  templateUrl: './fine-payment-history-detail.component.html',
})
export class FinePaymentHistoryDetailComponent implements OnInit {
  finePaymentHistory: IFinePaymentHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finePaymentHistory }) => (this.finePaymentHistory = finePaymentHistory));
  }

  previousState(): void {
    window.history.back();
  }
}
