import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { FestivalAllowancePaymentDtlComponent } from './festival-allowance-payment-dtl.component';
import { FestivalAllowancePaymentDtlDetailComponent } from './festival-allowance-payment-dtl-detail.component';
import { FestivalAllowancePaymentDtlUpdateComponent } from './festival-allowance-payment-dtl-update.component';
import { FestivalAllowancePaymentDtlDeleteDialogComponent } from './festival-allowance-payment-dtl-delete-dialog.component';
import { festivalAllowancePaymentDtlRoute } from './festival-allowance-payment-dtl.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(festivalAllowancePaymentDtlRoute)],
  declarations: [
    FestivalAllowancePaymentDtlComponent,
    FestivalAllowancePaymentDtlDetailComponent,
    FestivalAllowancePaymentDtlUpdateComponent,
    FestivalAllowancePaymentDtlDeleteDialogComponent,
  ],
  entryComponents: [FestivalAllowancePaymentDtlDeleteDialogComponent],
})
export class CodeNodeErpFestivalAllowancePaymentDtlModule {}
