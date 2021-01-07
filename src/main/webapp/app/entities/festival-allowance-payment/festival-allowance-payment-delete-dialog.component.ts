import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { FestivalAllowancePaymentService } from './festival-allowance-payment.service';

@Component({
  templateUrl: './festival-allowance-payment-delete-dialog.component.html',
})
export class FestivalAllowancePaymentDeleteDialogComponent {
  festivalAllowancePayment?: IFestivalAllowancePayment;

  constructor(
    protected festivalAllowancePaymentService: FestivalAllowancePaymentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.festivalAllowancePaymentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('festivalAllowancePaymentListModification');
      this.activeModal.close();
    });
  }
}
