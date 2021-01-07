import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';

@Component({
  selector: 'jhi-festival-allowance-payment-detail',
  templateUrl: './festival-allowance-payment-detail.component.html',
})
export class FestivalAllowancePaymentDetailComponent implements OnInit {
  festivalAllowancePayment: IFestivalAllowancePayment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivalAllowancePayment }) => (this.festivalAllowancePayment = festivalAllowancePayment));
  }

  previousState(): void {
    window.history.back();
  }
}
