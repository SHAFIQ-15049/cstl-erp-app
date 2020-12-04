import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { JobHistoryExtComponent } from './job-history-ext.component';
import { JobHistoryExtDetailComponent } from './job-history-ext-detail.component';
import { JobHistoryExtUpdateComponent } from './job-history-ext-update.component';
import { JobHistoryExtDeleteDialogComponent } from './job-history-ext-delete-dialog.component';
import { jobHistoryExtRoute } from './job-history-ext.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(jobHistoryExtRoute)],
  declarations: [JobHistoryExtComponent, JobHistoryExtDetailComponent, JobHistoryExtUpdateComponent, JobHistoryExtDeleteDialogComponent],
  entryComponents: [JobHistoryExtDeleteDialogComponent],
})
export class CodeNodeErpJobHistoryModule {}
