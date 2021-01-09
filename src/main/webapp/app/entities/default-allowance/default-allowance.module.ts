import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { DefaultAllowanceComponent } from './default-allowance.component';
import { DefaultAllowanceDetailComponent } from './default-allowance-detail.component';
import { DefaultAllowanceUpdateComponent } from './default-allowance-update.component';
import { DefaultAllowanceDeleteDialogComponent } from './default-allowance-delete-dialog.component';
import { defaultAllowanceRoute } from './default-allowance.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(defaultAllowanceRoute)],
  declarations: [
    DefaultAllowanceComponent,
    DefaultAllowanceDetailComponent,
    DefaultAllowanceUpdateComponent,
    DefaultAllowanceDeleteDialogComponent,
  ],
  entryComponents: [DefaultAllowanceDeleteDialogComponent],
})
export class CodeNodeErpDefaultAllowanceModule {}
