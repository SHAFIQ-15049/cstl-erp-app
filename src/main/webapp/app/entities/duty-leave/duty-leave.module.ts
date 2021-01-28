import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { DutyLeaveComponent } from './duty-leave.component';
import { DutyLeaveDetailComponent } from './duty-leave-detail.component';
import { dutyLeaveRoute } from './duty-leave.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(dutyLeaveRoute)],
  declarations: [DutyLeaveComponent, DutyLeaveDetailComponent],
})
export class CodeNodeErpDutyLeaveModule {}
