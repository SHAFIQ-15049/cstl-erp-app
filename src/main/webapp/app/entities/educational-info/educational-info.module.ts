import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EducationalInfoComponent } from './educational-info.component';
import { EducationalInfoDetailComponent } from './educational-info-detail.component';
import { EducationalInfoUpdateComponent } from './educational-info-update.component';
import { EducationalInfoDeleteDialogComponent } from './educational-info-delete-dialog.component';
import { educationalInfoRoute } from './educational-info.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(educationalInfoRoute)],
  // declarations: [
  //   EducationalInfoComponent,
  //   EducationalInfoDetailComponent,
  //   EducationalInfoUpdateComponent,
  //   EducationalInfoDeleteDialogComponent,
  // ],
  // entryComponents: [EducationalInfoDeleteDialogComponent],
})
export class CodeNodeErpEducationalInfoModule {}
