import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { ServiceHistoryComponent } from './service-history.component';
import { ServiceHistoryDetailComponent } from './service-history-detail.component';
import { ServiceHistoryUpdateComponent } from './service-history-update.component';
import { ServiceHistoryDeleteDialogComponent } from './service-history-delete-dialog.component';
import { serviceHistoryRoute } from './service-history.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(serviceHistoryRoute)],
  // declarations: [
  //   ServiceHistoryComponent,
  //   ServiceHistoryDetailComponent,
  //   ServiceHistoryUpdateComponent,
  //   ServiceHistoryDeleteDialogComponent,
  // ],
  // entryComponents: [ServiceHistoryDeleteDialogComponent],
})
export class CodeNodeErpServiceHistoryModule {}
