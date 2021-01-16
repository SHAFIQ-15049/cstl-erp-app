import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from './over-time.service';

@Component({
  templateUrl: './over-time-delete-dialog.component.html',
})
export class OverTimeDeleteDialogComponent {
  overTime?: IOverTime;

  constructor(protected overTimeService: OverTimeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.overTimeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('overTimeListModification');
      this.activeModal.close();
    });
  }
}
