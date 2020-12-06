import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AddressExtComponent } from './address-ext.component';
import { AddressExtDetailComponent } from './address-ext-detail.component';
import { AddressExtUpdateComponent } from './address-ext-update.component';
import { AddressExtDeleteDialogComponent } from './address-ext-delete-dialog.component';
import { addressExtRoute } from './address-ext.route';
import {AddressComponent} from "app/entities/address/address.component";
import {AddressDetailComponent} from "app/entities/address/address-detail.component";
import {AddressUpdateComponent} from "app/entities/address/address-update.component";
import {AddressDeleteDialogComponent} from "app/entities/address/address-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(addressExtRoute)],
  declarations: [ AddressComponent, AddressDetailComponent, AddressUpdateComponent, AddressDeleteDialogComponent, AddressExtComponent, AddressExtDetailComponent, AddressExtUpdateComponent, AddressExtDeleteDialogComponent],
  entryComponents: [AddressExtDeleteDialogComponent],
})
export class CodeNodeErpAddressModule {}
