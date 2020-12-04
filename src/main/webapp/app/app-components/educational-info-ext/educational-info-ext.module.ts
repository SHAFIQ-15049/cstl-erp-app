import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EducationalInfoExtComponent } from './educational-info-ext.component';
import { EducationalInfoExtDetailComponent } from './educational-info-ext-detail.component';
import { EducationalInfoExtUpdateComponent } from './educational-info-ext-update.component';
import { EducationalInfoExtDeleteDialogComponent } from './educational-info-ext-delete-dialog.component';
import { educationalInfoExtRoute } from './educational-info-ext.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(educationalInfoExtRoute)],
  declarations: [
    EducationalInfoExtComponent,
    EducationalInfoExtDetailComponent,
    EducationalInfoExtUpdateComponent,
    EducationalInfoExtDeleteDialogComponent,
  ],
  entryComponents: [EducationalInfoExtDeleteDialogComponent],
})
export class CodeNodeErpEducationalInfoModule {}
