import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekend } from 'app/shared/model/weekend.model';
import { WeekendService } from './weekend.service';

@Component({
  templateUrl: './weekend-delete-dialog.component.html',
})
export class WeekendDeleteDialogComponent {
  weekend?: IWeekend;

  constructor(protected weekendService: WeekendService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.weekendService.delete(id).subscribe(() => {
      this.eventManager.broadcast('weekendListModification');
      this.activeModal.close();
    });
  }
}
