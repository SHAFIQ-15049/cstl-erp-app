import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILine } from 'app/shared/model/line.model';
import { LineService } from './line.service';

@Component({
  templateUrl: './line-delete-dialog.component.html',
})
export class LineDeleteDialogComponent {
  line?: ILine;

  constructor(protected lineService: LineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lineListModification');
      this.activeModal.close();
    });
  }
}
