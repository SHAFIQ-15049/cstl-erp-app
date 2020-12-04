import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { TrainingComponent } from './training.component';
import { TrainingDetailComponent } from './training-detail.component';
import { TrainingUpdateComponent } from './training-update.component';
import { TrainingDeleteDialogComponent } from './training-delete-dialog.component';
import { trainingRoute } from './training.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(trainingRoute)],
  // declarations: [TrainingComponent, TrainingDetailComponent, TrainingUpdateComponent, TrainingDeleteDialogComponent],
  // entryComponents: [TrainingDeleteDialogComponent],
})
export class CodeNodeErpTrainingModule {}
