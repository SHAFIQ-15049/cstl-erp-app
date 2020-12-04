import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { ServiceHistoryExtComponent } from './service-history-ext.component';
import { ServiceHistoryExtDetailComponent } from './service-history-ext-detail.component';
import { ServiceHistoryExtUpdateComponent } from './service-history-ext-update.component';
import { ServiceHistoryExtDeleteDialogComponent } from './service-history-ext-delete-dialog.component';
import { serviceHistoryExtRoute } from './service-history-ext.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(serviceHistoryExtRoute)],
  declarations: [
    ServiceHistoryExtComponent,
    ServiceHistoryExtDetailComponent,
    ServiceHistoryExtUpdateComponent,
    ServiceHistoryExtDeleteDialogComponent,
  ],
  entryComponents: [ServiceHistoryExtDeleteDialogComponent],
})
export class CodeNodeErpServiceHistoryModule {}
