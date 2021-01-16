import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { OverTimeComponent } from './over-time.component';
import { OverTimeDetailComponent } from './over-time-detail.component';
import { OverTimeUpdateComponent } from './over-time-update.component';
import { OverTimeDeleteDialogComponent } from './over-time-delete-dialog.component';
import { overTimeRoute } from './over-time.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(overTimeRoute)],
  declarations: [OverTimeComponent, OverTimeDetailComponent, OverTimeUpdateComponent, OverTimeDeleteDialogComponent],
  entryComponents: [OverTimeDeleteDialogComponent],
})
export class CodeNodeErpOverTimeModule {}
