import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeExtComponent } from './employee-ext.component';
import { EmployeeExtDetailComponent } from './employee-ext-detail.component';
import { EmployeeExtUpdateComponent } from './employee-ext-update.component';
import { EmployeeExtDeleteDialogComponent } from './employee-ext-delete-dialog.component';
import { employeeExtRoute } from './employee-ext.route';
import { EmployeeComponent } from 'app/entities/employee/employee.component';
import { EmployeeDetailComponent } from 'app/entities/employee/employee-detail.component';
import { EmployeeUpdateComponent } from 'app/entities/employee/employee-update.component';
import { EmployeeDeleteDialogComponent } from 'app/entities/employee/employee-delete-dialog.component';
import { IdCardComponent } from './id-card/id-card.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule, PdfViewerModule, RouterModule.forChild(employeeExtRoute)],
  declarations: [
    EmployeeComponent,
    EmployeeDetailComponent,
    EmployeeUpdateComponent,
    EmployeeDeleteDialogComponent,
    EmployeeExtComponent,
    EmployeeExtDetailComponent,
    EmployeeExtUpdateComponent,
    EmployeeExtDeleteDialogComponent,
    IdCardComponent,
  ],
  entryComponents: [EmployeeExtDeleteDialogComponent],
})
export class CodeNodeErpEmployeeModule {}
