import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { FinePaymentHistoryComponent } from './fine-payment-history.component';
import { FinePaymentHistoryDetailComponent } from './fine-payment-history-detail.component';
import { FinePaymentHistoryUpdateComponent } from './fine-payment-history-update.component';
import { FinePaymentHistoryDeleteDialogComponent } from './fine-payment-history-delete-dialog.component';
import { finePaymentHistoryRoute } from './fine-payment-history.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(finePaymentHistoryRoute)],
  declarations: [
    FinePaymentHistoryComponent,
    FinePaymentHistoryDetailComponent,
    FinePaymentHistoryUpdateComponent,
    FinePaymentHistoryDeleteDialogComponent,
  ],
  entryComponents: [FinePaymentHistoryDeleteDialogComponent],
})
export class CodeNodeErpFinePaymentHistoryModule {}
