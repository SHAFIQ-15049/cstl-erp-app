import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { LeaveApplicationComponent } from './leave-application.component';
import { LeaveApplicationDetailComponent } from './leave-application-detail.component';
import { LeaveApplicationUpdateComponent } from './leave-application-update.component';
import { LeaveApplicationDeleteDialogComponent } from './leave-application-delete-dialog.component';
import { leaveApplicationRoute } from './leave-application.route';
import { LeaveApplicationReviewComponent } from 'app/entities/leave-application/leave-application-review.component';
import { LeaveApplicationActionUpdateComponent } from 'app/entities/leave-application/leave-application-action-update.component';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(leaveApplicationRoute)],
  declarations: [
    LeaveApplicationComponent,
    LeaveApplicationDetailComponent,
    LeaveApplicationUpdateComponent,
    LeaveApplicationDeleteDialogComponent,
    LeaveApplicationReviewComponent,
    LeaveApplicationActionUpdateComponent,
  ],
  entryComponents: [LeaveApplicationDeleteDialogComponent],
})
export class CodeNodeErpLeaveApplicationModule {}