import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceHistory } from 'app/shared/model/service-history.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ServiceHistoryExtService } from './service-history-ext.service';
import { ServiceHistoryExtDeleteDialogComponent } from './service-history-ext-delete-dialog.component';
import {ServiceHistoryComponent} from "app/entities/service-history/service-history.component";

@Component({
  selector: 'jhi-service-history',
  templateUrl: './service-history-ext.component.html',
})
export class ServiceHistoryExtComponent extends ServiceHistoryComponent implements OnInit, OnDestroy {


  constructor(
    protected serviceHistoryService: ServiceHistoryExtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(serviceHistoryService, activatedRoute, router, eventManager, modalService);
  }

}
