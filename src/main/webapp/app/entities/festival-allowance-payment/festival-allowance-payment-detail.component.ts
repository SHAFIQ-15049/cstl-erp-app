import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router, RouteReuseStrategy } from '@angular/router';

import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { flatMap } from 'rxjs/operators';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';

@Component({
  selector: 'jhi-festival-allowance-payment-detail',
  templateUrl: './festival-allowance-payment-detail.component.html',
})
export class FestivalAllowancePaymentDetailComponent implements OnInit {
  festivalAllowancePayment: IFestivalAllowancePayment | null = null;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private festivalAllowancePaymentService: FestivalAllowancePaymentService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivalAllowancePayment }) => {
      this.festivalAllowancePayment = festivalAllowancePayment;
      this.festivalAllowancePaymentService.storeFestivalAllowanceId(this.festivalAllowancePayment?.id!);
      // this.router.navigate(['festival-allowance-payment-dtl'], {relativeTo: this.activatedRoute});
    });
  }

  previousState(): void {
    window.history.back();
  }
}
