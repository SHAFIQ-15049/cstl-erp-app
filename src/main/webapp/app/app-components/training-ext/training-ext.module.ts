import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { TrainingExtComponent } from './training-ext.component';
import { TrainingExtDetailComponent } from './training-ext-detail.component';
import { TrainingExtUpdateComponent } from './training-ext-update.component';
import { TrainingExtDeleteDialogComponent } from './training-ext-delete-dialog.component';
import { trainingExtRoute } from './training-ext.route';
import {TrainingComponent} from "app/entities/training/training.component";
import {TrainingDetailComponent} from "app/entities/training/training-detail.component";
import {TrainingUpdateComponent} from "app/entities/training/training-update.component";
import {TrainingDeleteDialogComponent} from "app/entities/training/training-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(trainingExtRoute)],
  declarations: [TrainingComponent, TrainingDetailComponent, TrainingUpdateComponent, TrainingDeleteDialogComponent, TrainingExtComponent, TrainingExtDetailComponent, TrainingExtUpdateComponent, TrainingExtDeleteDialogComponent],
  entryComponents: [TrainingExtDeleteDialogComponent],
})
export class CodeNodeErpTrainingModule {}
