import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';

@Component({
  selector: 'jhi-festival-allowance-payment-dtl-detail',
  templateUrl: './festival-allowance-payment-dtl-detail.component.html',
})
export class FestivalAllowancePaymentDtlDetailComponent implements OnInit {
  festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ festivalAllowancePaymentDtl }) => (this.festivalAllowancePaymentDtl = festivalAllowancePaymentDtl)
    );
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
