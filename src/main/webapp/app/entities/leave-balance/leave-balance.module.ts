import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { LeaveBalanceComponent } from './leave-balance.component';
import { LeaveBalanceDetailComponent } from './leave-balance-detail.component';
import { leaveBalanceRoute } from './leave-balance.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(leaveBalanceRoute)],
  declarations: [LeaveBalanceComponent, LeaveBalanceDetailComponent],
})
export class CodeNodeErpLeaveBalanceModule {}
