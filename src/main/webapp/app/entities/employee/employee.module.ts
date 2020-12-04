import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeComponent } from './employee.component';
import { EmployeeDetailComponent } from './employee-detail.component';
import { EmployeeUpdateComponent } from './employee-update.component';
import { EmployeeDeleteDialogComponent } from './employee-delete-dialog.component';
import { employeeRoute } from './employee.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(employeeRoute)],
  // declarations: [EmployeeComponent, EmployeeDetailComponent, EmployeeUpdateComponent, EmployeeDeleteDialogComponent],
  // entryComponents: [EmployeeDeleteDialogComponent],
})
export class CodeNodeErpEmployeeModule {}
