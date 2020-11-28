import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITraining } from 'app/shared/model/training.model';
import { TrainingService } from './training.service';

@Component({
  templateUrl: './training-delete-dialog.component.html',
})
export class TrainingDeleteDialogComponent {
  training?: ITraining;

  constructor(protected trainingService: TrainingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingListModification');
      this.activeModal.close();
    });
  }
}
