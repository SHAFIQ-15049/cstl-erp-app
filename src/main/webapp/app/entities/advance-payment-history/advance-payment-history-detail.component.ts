import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';

@Component({
  selector: 'jhi-advance-payment-history-detail',
  templateUrl: './advance-payment-history-detail.component.html',
})
export class AdvancePaymentHistoryDetailComponent implements OnInit {
  advancePaymentHistory: IAdvancePaymentHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advancePaymentHistory }) => (this.advancePaymentHistory = advancePaymentHistory));
  }

  previousState(): void {
    window.history.back();
  }
}
