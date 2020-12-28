import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AdvanceComponent } from './advance.component';
import { AdvanceDetailComponent } from './advance-detail.component';
import { AdvanceUpdateComponent } from './advance-update.component';
import { AdvanceDeleteDialogComponent } from './advance-delete-dialog.component';
import { advanceRoute } from './advance.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(advanceRoute)],
  declarations: [AdvanceComponent, AdvanceDetailComponent, AdvanceUpdateComponent, AdvanceDeleteDialogComponent],
  entryComponents: [AdvanceDeleteDialogComponent],
})
export class CodeNodeErpAdvanceModule {}
