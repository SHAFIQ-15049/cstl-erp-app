import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFine } from 'app/shared/model/fine.model';
import { FineService } from './fine.service';

@Component({
  templateUrl: './fine-delete-dialog.component.html',
})
export class FineDeleteDialogComponent {
  fine?: IFine;

  constructor(protected fineService: FineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fineListModification');
      this.activeModal.close();
    });
  }
}
