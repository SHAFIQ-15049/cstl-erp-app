import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AdvancePaymentHistoryComponent } from './advance-payment-history.component';
import { AdvancePaymentHistoryDetailComponent } from './advance-payment-history-detail.component';
import { AdvancePaymentHistoryUpdateComponent } from './advance-payment-history-update.component';
import { AdvancePaymentHistoryDeleteDialogComponent } from './advance-payment-history-delete-dialog.component';
import { advancePaymentHistoryRoute } from './advance-payment-history.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(advancePaymentHistoryRoute)],
  declarations: [
    AdvancePaymentHistoryComponent,
    AdvancePaymentHistoryDetailComponent,
    AdvancePaymentHistoryUpdateComponent,
    AdvancePaymentHistoryDeleteDialogComponent,
  ],
  entryComponents: [AdvancePaymentHistoryDeleteDialogComponent],
})
export class CodeNodeErpAdvancePaymentHistoryModule {}
