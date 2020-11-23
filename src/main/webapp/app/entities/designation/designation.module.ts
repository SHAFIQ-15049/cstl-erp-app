import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { DesignationComponent } from './designation.component';
import { DesignationDetailComponent } from './designation-detail.component';
import { DesignationUpdateComponent } from './designation-update.component';
import { DesignationDeleteDialogComponent } from './designation-delete-dialog.component';
import { designationRoute } from './designation.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(designationRoute)],
  declarations: [DesignationComponent, DesignationDetailComponent, DesignationUpdateComponent, DesignationDeleteDialogComponent],
  entryComponents: [DesignationDeleteDialogComponent],
})
export class CodeNodeErpDesignationModule {}
