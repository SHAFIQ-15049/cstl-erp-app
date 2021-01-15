import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AttendanceSummaryComponent } from './attendance-summary.component';
import { attendanceSummaryRoute } from './attendance-summary.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(attendanceSummaryRoute)],
  declarations: [AttendanceSummaryComponent],
})
export class CodeNodeErpAttendanceSummaryModule {}
