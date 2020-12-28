import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';
import { AdvancePaymentHistoryService } from './advance-payment-history.service';

@Component({
  templateUrl: './advance-payment-history-delete-dialog.component.html',
})
export class AdvancePaymentHistoryDeleteDialogComponent {
  advancePaymentHistory?: IAdvancePaymentHistory;

  constructor(
    protected advancePaymentHistoryService: AdvancePaymentHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.advancePaymentHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('advancePaymentHistoryListModification');
      this.activeModal.close();
    });
  }
}
