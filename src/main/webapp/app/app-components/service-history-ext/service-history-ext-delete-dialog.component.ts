import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceHistory } from 'app/shared/model/service-history.model';
import { ServiceHistoryExtService } from './service-history-ext.service';
import {ServiceHistoryDeleteDialogComponent} from "app/entities/service-history/service-history-delete-dialog.component";

@Component({
  templateUrl: './service-history-ext-delete-dialog.component.html',
})
export class ServiceHistoryExtDeleteDialogComponent extends ServiceHistoryDeleteDialogComponent{

  constructor(
    protected serviceHistoryService: ServiceHistoryExtService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(serviceHistoryService, activeModal, eventManager);
  }

}
