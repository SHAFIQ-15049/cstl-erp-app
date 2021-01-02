import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { MonthlySalaryComponent } from './monthly-salary.component';
import { MonthlySalaryDetailComponent } from './monthly-salary-detail.component';
import { MonthlySalaryUpdateComponent } from './monthly-salary-update.component';
import { MonthlySalaryDeleteDialogComponent } from './monthly-salary-delete-dialog.component';
import { monthlySalaryRoute } from './monthly-salary.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(monthlySalaryRoute)],
  declarations: [MonthlySalaryComponent, MonthlySalaryDetailComponent, MonthlySalaryUpdateComponent, MonthlySalaryDeleteDialogComponent],
  entryComponents: [MonthlySalaryDeleteDialogComponent],
})
export class CodeNodeErpMonthlySalaryModule {}
