import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { FestivalAllowancePaymentComponent } from './festival-allowance-payment.component';
import { FestivalAllowancePaymentDetailComponent } from './festival-allowance-payment-detail.component';
import { FestivalAllowancePaymentUpdateComponent } from './festival-allowance-payment-update.component';
import { FestivalAllowancePaymentDeleteDialogComponent } from './festival-allowance-payment-delete-dialog.component';
import { festivalAllowancePaymentRoute } from './festival-allowance-payment.route';
import { FestivalAllowanceEmployeeComponent } from './festival-allowance-employee/festival-allowance-employee.component';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(festivalAllowancePaymentRoute)],
  declarations: [
    FestivalAllowancePaymentComponent,
    FestivalAllowancePaymentDetailComponent,
    FestivalAllowancePaymentUpdateComponent,
    FestivalAllowancePaymentDeleteDialogComponent,
    FestivalAllowanceEmployeeComponent,
  ],
  entryComponents: [FestivalAllowancePaymentDeleteDialogComponent],
})
export class CodeNodeErpFestivalAllowancePaymentModule {}
