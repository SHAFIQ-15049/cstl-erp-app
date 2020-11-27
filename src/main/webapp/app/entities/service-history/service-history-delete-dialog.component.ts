import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceHistory } from 'app/shared/model/service-history.model';
import { ServiceHistoryService } from './service-history.service';

@Component({
  templateUrl: './service-history-delete-dialog.component.html',
})
export class ServiceHistoryDeleteDialogComponent {
  serviceHistory?: IServiceHistory;

  constructor(
    protected serviceHistoryService: ServiceHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceHistoryListModification');
      this.activeModal.close();
    });
  }
}
