import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { ServiceHistoryExtComponent } from './service-history-ext.component';
import { ServiceHistoryExtDetailComponent } from './service-history-ext-detail.component';
import { ServiceHistoryExtUpdateComponent } from './service-history-ext-update.component';
import { ServiceHistoryExtDeleteDialogComponent } from './service-history-ext-delete-dialog.component';
import { serviceHistoryExtRoute } from './service-history-ext.route';
import {ServiceHistoryComponent} from "app/entities/service-history/service-history.component";
import {ServiceHistoryDetailComponent} from "app/entities/service-history/service-history-detail.component";
import {ServiceHistoryUpdateComponent} from "app/entities/service-history/service-history-update.component";
import {ServiceHistoryDeleteDialogComponent} from "app/entities/service-history/service-history-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(serviceHistoryExtRoute)],
  declarations: [
    ServiceHistoryComponent,
    ServiceHistoryDetailComponent,
    ServiceHistoryUpdateComponent,
    ServiceHistoryDeleteDialogComponent,
    ServiceHistoryExtComponent,
    ServiceHistoryExtDetailComponent,
    ServiceHistoryExtUpdateComponent,
    ServiceHistoryExtDeleteDialogComponent,
  ],
  entryComponents: [ServiceHistoryExtDeleteDialogComponent],
})
export class CodeNodeErpServiceHistoryModule {}
