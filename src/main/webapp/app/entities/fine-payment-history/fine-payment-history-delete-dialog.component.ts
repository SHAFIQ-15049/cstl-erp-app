import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinePaymentHistory } from 'app/shared/model/fine-payment-history.model';
import { FinePaymentHistoryService } from './fine-payment-history.service';

@Component({
  templateUrl: './fine-payment-history-delete-dialog.component.html',
})
export class FinePaymentHistoryDeleteDialogComponent {
  finePaymentHistory?: IFinePaymentHistory;

  constructor(
    protected finePaymentHistoryService: FinePaymentHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.finePaymentHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('finePaymentHistoryListModification');
      this.activeModal.close();
    });
  }
}
