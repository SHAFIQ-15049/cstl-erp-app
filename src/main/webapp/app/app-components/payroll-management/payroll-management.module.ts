import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PayrollManagementRoutingModule } from './payroll-management-routing.module';
import { PayrollManagementComponent } from './payroll-management.component';
import { FormsModule } from '@angular/forms';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Select2Module } from 'ng-select2-component';
import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { PayrollReportComponent } from './payroll-report.component';

@NgModule({
  declarations: [PayrollManagementComponent, PayrollReportComponent],
  imports: [CommonModule, PayrollManagementRoutingModule, FormsModule, NgbTypeaheadModule, Select2Module, CodeNodeErpSharedModule],
})
export class PayrollManagementModule {}
