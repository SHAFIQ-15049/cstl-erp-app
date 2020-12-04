import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { TrainingExtComponent } from './training-ext.component';
import { TrainingExtDetailComponent } from './training-ext-detail.component';
import { TrainingExtUpdateComponent } from './training-ext-update.component';
import { TrainingExtDeleteDialogComponent } from './training-ext-delete-dialog.component';
import { trainingExtRoute } from './training-ext.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(trainingExtRoute)],
  declarations: [TrainingExtComponent, TrainingExtDetailComponent, TrainingExtUpdateComponent, TrainingExtDeleteDialogComponent],
  entryComponents: [TrainingExtDeleteDialogComponent],
})
export class CodeNodeErpTrainingModule {}
