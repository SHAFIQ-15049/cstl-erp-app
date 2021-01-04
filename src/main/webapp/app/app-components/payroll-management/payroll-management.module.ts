import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PayrollManagementRoutingModule } from './payroll-management-routing.module';
import { PayrollManagementComponent } from './payroll-management.component';


@NgModule({
  declarations: [PayrollManagementComponent],
  imports: [
    CommonModule,
    PayrollManagementRoutingModule
  ]
})
export class PayrollManagementModule { }
