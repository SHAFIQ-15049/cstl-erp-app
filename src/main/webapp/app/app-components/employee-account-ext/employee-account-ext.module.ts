import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeAccountExtComponent } from './employee-account-ext.component';
import { EmployeeAccountExtDetailComponent } from './employee-account-ext-detail.component';
import { EmployeeAccountExtUpdateComponent } from './employee-account-ext-update.component';
import { EmployeeAccountExtDeleteDialogComponent } from './employee-account-ext-delete-dialog.component';
import { employeeAccountExtRoute } from './employee-account-ext.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(employeeAccountExtRoute)],
  declarations: [
    EmployeeAccountExtComponent,
    EmployeeAccountExtDetailComponent,
    EmployeeAccountExtUpdateComponent,
    EmployeeAccountExtDeleteDialogComponent,
  ],
  entryComponents: [EmployeeAccountExtDeleteDialogComponent],
})
export class CodeNodeErpEmployeeAccountModule {}
