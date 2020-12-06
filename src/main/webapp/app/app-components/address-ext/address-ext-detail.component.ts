import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddress } from 'app/shared/model/address.model';
import {AddressDetailComponent} from "app/entities/address/address-detail.component";

@Component({
  selector: 'jhi-address-detail',
  templateUrl: './address-ext-detail.component.html',
})
export class AddressExtDetailComponent extends AddressDetailComponent implements OnInit {

  constructor(protected activatedRoute: ActivatedRoute) {
    super(activatedRoute);
  }

}
