import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { LeaveTypeComponent } from './leave-type.component';
import { LeaveTypeDetailComponent } from './leave-type-detail.component';
import { LeaveTypeUpdateComponent } from './leave-type-update.component';
import { LeaveTypeDeleteDialogComponent } from './leave-type-delete-dialog.component';
import { leaveTypeRoute } from './leave-type.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(leaveTypeRoute)],
  declarations: [LeaveTypeComponent, LeaveTypeDetailComponent, LeaveTypeUpdateComponent, LeaveTypeDeleteDialogComponent],
  entryComponents: [LeaveTypeDeleteDialogComponent],
})
export class CodeNodeErpLeaveTypeModule {}
