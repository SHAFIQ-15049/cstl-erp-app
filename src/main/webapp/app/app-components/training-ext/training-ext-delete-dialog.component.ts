import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITraining } from 'app/shared/model/training.model';
import { TrainingExtService } from './training-ext.service';
import {TrainingDeleteDialogComponent} from "app/entities/training/training-delete-dialog.component";

@Component({
  templateUrl: './training-ext-delete-dialog.component.html',
})
export class TrainingExtDeleteDialogComponent extends TrainingDeleteDialogComponent{

  constructor(protected trainingService: TrainingExtService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {
    super(trainingService, activeModal, eventManager);
  }
}
