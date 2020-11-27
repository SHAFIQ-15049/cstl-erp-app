import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDivision } from 'app/shared/model/division.model';
import { DivisionService } from './division.service';

@Component({
  templateUrl: './division-delete-dialog.component.html',
})
export class DivisionDeleteDialogComponent {
  division?: IDivision;

  constructor(protected divisionService: DivisionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.divisionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('divisionListModification');
      this.activeModal.close();
    });
  }
}
