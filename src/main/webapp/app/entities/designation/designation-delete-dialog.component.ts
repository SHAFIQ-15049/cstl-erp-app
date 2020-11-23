import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from './designation.service';

@Component({
  templateUrl: './designation-delete-dialog.component.html',
})
export class DesignationDeleteDialogComponent {
  designation?: IDesignation;

  constructor(
    protected designationService: DesignationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.designationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('designationListModification');
      this.activeModal.close();
    });
  }
}
