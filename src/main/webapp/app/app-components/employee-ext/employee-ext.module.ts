import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeExtComponent } from './employee-ext.component';
import { EmployeeExtDetailComponent } from './employee-ext-detail.component';
import { EmployeeExtUpdateComponent } from './employee-ext-update.component';
import { EmployeeExtDeleteDialogComponent } from './employee-ext-delete-dialog.component';
import { employeeExtRoute } from './employee-ext.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(employeeExtRoute)],
  declarations: [EmployeeExtComponent, EmployeeExtDetailComponent, EmployeeExtUpdateComponent, EmployeeExtDeleteDialogComponent],
  entryComponents: [EmployeeExtDeleteDialogComponent],
})
export class CodeNodeErpEmployeeModule {}
