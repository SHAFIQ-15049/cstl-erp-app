import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { MonthlySalaryDtlComponent } from './monthly-salary-dtl.component';
import { MonthlySalaryDtlDetailComponent } from './monthly-salary-dtl-detail.component';
import { MonthlySalaryDtlUpdateComponent } from './monthly-salary-dtl-update.component';
import { MonthlySalaryDtlDeleteDialogComponent } from './monthly-salary-dtl-delete-dialog.component';
import { monthlySalaryDtlRoute } from './monthly-salary-dtl.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(monthlySalaryDtlRoute)],
  declarations: [
    MonthlySalaryDtlComponent,
    MonthlySalaryDtlDetailComponent,
    MonthlySalaryDtlUpdateComponent,
    MonthlySalaryDtlDeleteDialogComponent,
  ],
  entryComponents: [MonthlySalaryDtlDeleteDialogComponent],
})
export class CodeNodeErpMonthlySalaryDtlModule {}
