import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { IdCardManagementComponent } from './id-card-management.component';
import { IdCardManagementDetailComponent } from './id-card-management-detail.component';
import { IdCardManagementUpdateComponent } from './id-card-management-update.component';
import { IdCardManagementDeleteDialogComponent } from './id-card-management-delete-dialog.component';
import { idCardManagementRoute } from './id-card-management.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(idCardManagementRoute)],
  declarations: [
    IdCardManagementComponent,
    IdCardManagementDetailComponent,
    IdCardManagementUpdateComponent,
    IdCardManagementDeleteDialogComponent,
  ],
  entryComponents: [IdCardManagementDeleteDialogComponent],
})
export class CodeNodeErpIdCardManagementModule {}
