import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';
import { FestivalAllowancePaymentDtlService } from './festival-allowance-payment-dtl.service';

@Component({
  templateUrl: './festival-allowance-payment-dtl-delete-dialog.component.html',
})
export class FestivalAllowancePaymentDtlDeleteDialogComponent {
  festivalAllowancePaymentDtl?: IFestivalAllowancePaymentDtl;

  constructor(
    protected festivalAllowancePaymentDtlService: FestivalAllowancePaymentDtlService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.festivalAllowancePaymentDtlService.delete(id).subscribe(() => {
      this.eventManager.broadcast('festivalAllowancePaymentDtlListModification');
      this.activeModal.close();
    });
  }
}
