import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { PartialSalaryComponent } from './partial-salary.component';
import { PartialSalaryDetailComponent } from './partial-salary-detail.component';
import { PartialSalaryUpdateComponent } from './partial-salary-update.component';
import { PartialSalaryDeleteDialogComponent } from './partial-salary-delete-dialog.component';
import { partialSalaryRoute } from './partial-salary.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(partialSalaryRoute)],
  declarations: [PartialSalaryComponent, PartialSalaryDetailComponent, PartialSalaryUpdateComponent, PartialSalaryDeleteDialogComponent],
  entryComponents: [PartialSalaryDeleteDialogComponent],
})
export class CodeNodeErpPartialSalaryModule {}
