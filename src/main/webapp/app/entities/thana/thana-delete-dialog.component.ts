import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThana } from 'app/shared/model/thana.model';
import { ThanaService } from './thana.service';

@Component({
  templateUrl: './thana-delete-dialog.component.html',
})
export class ThanaDeleteDialogComponent {
  thana?: IThana;

  constructor(protected thanaService: ThanaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thanaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('thanaListModification');
      this.activeModal.close();
    });
  }
}
