import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { JobHistoryExtComponent } from './job-history-ext.component';
import { JobHistoryExtDetailComponent } from './job-history-ext-detail.component';
import { JobHistoryExtUpdateComponent } from './job-history-ext-update.component';
import { JobHistoryExtDeleteDialogComponent } from './job-history-ext-delete-dialog.component';
import { jobHistoryExtRoute } from './job-history-ext.route';
import {JobHistoryComponent} from "app/entities/job-history/job-history.component";
import {JobHistoryDetailComponent} from "app/entities/job-history/job-history-detail.component";
import {JobHistoryUpdateComponent} from "app/entities/job-history/job-history-update.component";
import {JobHistoryDeleteDialogComponent} from "app/entities/job-history/job-history-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(jobHistoryExtRoute)],
  declarations: [JobHistoryComponent, JobHistoryDetailComponent, JobHistoryUpdateComponent, JobHistoryDeleteDialogComponent,JobHistoryExtComponent, JobHistoryExtDetailComponent, JobHistoryExtUpdateComponent, JobHistoryExtDeleteDialogComponent],
  entryComponents: [JobHistoryExtDeleteDialogComponent],
})
export class CodeNodeErpJobHistoryModule {}
