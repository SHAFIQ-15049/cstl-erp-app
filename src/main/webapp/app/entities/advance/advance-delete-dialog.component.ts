import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvance } from 'app/shared/model/advance.model';
import { AdvanceService } from './advance.service';

@Component({
  templateUrl: './advance-delete-dialog.component.html',
})
export class AdvanceDeleteDialogComponent {
  advance?: IAdvance;

  constructor(protected advanceService: AdvanceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.advanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('advanceListModification');
      this.activeModal.close();
    });
  }
}
