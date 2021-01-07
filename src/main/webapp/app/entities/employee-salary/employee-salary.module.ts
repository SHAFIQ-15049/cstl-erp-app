import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeSalaryComponent } from './employee-salary.component';
import { EmployeeSalaryDetailComponent } from './employee-salary-detail.component';
import { EmployeeSalaryUpdateComponent } from './employee-salary-update.component';
import { EmployeeSalaryDeleteDialogComponent } from './employee-salary-delete-dialog.component';
import { employeeSalaryRoute } from './employee-salary.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(employeeSalaryRoute)],
  declarations: [
    EmployeeSalaryComponent,
    EmployeeSalaryDetailComponent,
    EmployeeSalaryUpdateComponent,
    EmployeeSalaryDeleteDialogComponent,
  ],
  entryComponents: [EmployeeSalaryDeleteDialogComponent],
})
export class CodeNodeErpEmployeeSalaryModule {}
