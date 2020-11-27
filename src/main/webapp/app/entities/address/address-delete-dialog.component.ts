import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from './address.service';

@Component({
  templateUrl: './address-delete-dialog.component.html',
})
export class AddressDeleteDialogComponent {
  address?: IAddress;

  constructor(protected addressService: AddressService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.addressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('addressListModification');
      this.activeModal.close();
    });
  }
}
