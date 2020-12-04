import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddress } from 'app/shared/model/address.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AddressExtService } from './address-ext.service';
import { AddressExtDeleteDialogComponent } from './address-ext-delete-dialog.component';
import {AddressComponent} from "app/entities/address/address.component";

@Component({
  selector: 'jhi-address',
  templateUrl: './address-ext.component.html',
})
export class AddressExtComponent extends AddressComponent implements OnInit, OnDestroy {

  constructor(
    protected addressService: AddressExtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(addressService, activatedRoute, router, eventManager, modalService);
  }

}
