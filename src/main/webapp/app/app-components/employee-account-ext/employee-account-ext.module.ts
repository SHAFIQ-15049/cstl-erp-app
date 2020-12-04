import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EmployeeAccountExtComponent } from './employee-account-ext.component';
import { EmployeeAccountExtDetailComponent } from './employee-account-ext-detail.component';
import { EmployeeAccountExtUpdateComponent } from './employee-account-ext-update.component';
import { EmployeeAccountExtDeleteDialogComponent } from './employee-account-ext-delete-dialog.component';
import { employeeAccountExtRoute } from './employee-account-ext.route';
import {EmployeeAccountComponent} from "app/entities/employee-account/employee-account.component";
import {EmployeeAccountDetailComponent} from "app/entities/employee-account/employee-account-detail.component";
import {EmployeeAccountUpdateComponent} from "app/entities/employee-account/employee-account-update.component";
import {EmployeeAccountDeleteDialogComponent} from "app/entities/employee-account/employee-account-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(employeeAccountExtRoute)],
  declarations: [
    EmployeeAccountComponent,
    EmployeeAccountDetailComponent,
    EmployeeAccountUpdateComponent,
    EmployeeAccountDeleteDialogComponent,
    EmployeeAccountExtComponent,
    EmployeeAccountExtDetailComponent,
    EmployeeAccountExtUpdateComponent,
    EmployeeAccountExtDeleteDialogComponent,
  ],
  entryComponents: [EmployeeAccountExtDeleteDialogComponent],
})
export class CodeNodeErpEmployeeAccountModule {}
