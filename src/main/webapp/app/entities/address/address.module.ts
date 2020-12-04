import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AddressComponent } from './address.component';
import { AddressDetailComponent } from './address-detail.component';
import { AddressUpdateComponent } from './address-update.component';
import { AddressDeleteDialogComponent } from './address-delete-dialog.component';
import { addressRoute } from './address.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(addressRoute)],
  // declarations: [AddressComponent, AddressDetailComponent, AddressUpdateComponent, AddressDeleteDialogComponent],
  // entryComponents: [AddressDeleteDialogComponent],
})
export class CodeNodeErpAddressModule {}
