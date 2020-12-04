import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddress } from 'app/shared/model/address.model';
import { AddressExtService } from './address-ext.service';
import {AddressDeleteDialogComponent} from "app/entities/address/address-delete-dialog.component";

@Component({
  templateUrl: './address-ext-delete-dialog.component.html',
})
export class AddressExtDeleteDialogComponent extends AddressDeleteDialogComponent{
  address?: IAddress;

  constructor(protected addressService: AddressExtService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {
    super(addressService, activeModal, eventManager);
  }

}
