import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from './grade.service';

@Component({
  templateUrl: './grade-delete-dialog.component.html',
})
export class GradeDeleteDialogComponent {
  grade?: IGrade;

  constructor(protected gradeService: GradeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gradeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gradeListModification');
      this.activeModal.close();
    });
  }
}
