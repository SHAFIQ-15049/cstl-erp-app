import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeAccountComponent } from './employee-account.component';
import { EmployeeAccountDetailComponent } from './employee-account-detail.component';
import { EmployeeAccountUpdateComponent } from './employee-account-update.component';
import { EmployeeAccountDeleteDialogComponent } from './employee-account-delete-dialog.component';
import { employeeAccountRoute } from './employee-account.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(employeeAccountRoute)],
  // declarations: [
  //   EmployeeAccountComponent,
  //   EmployeeAccountDetailComponent,
  //   EmployeeAccountUpdateComponent,
  //   EmployeeAccountDeleteDialogComponent,
  // ],
  // entryComponents: [EmployeeAccountDeleteDialogComponent],
})
export class CodeNodeErpEmployeeAccountModule {}
