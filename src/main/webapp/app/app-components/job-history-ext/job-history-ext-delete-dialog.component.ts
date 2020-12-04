import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryExtService } from './job-history-ext.service';
import {JobHistoryDeleteDialogComponent} from "app/entities/job-history/job-history-delete-dialog.component";

@Component({
  templateUrl: './job-history-ext-delete-dialog.component.html',
})
export class JobHistoryExtDeleteDialogComponent extends JobHistoryDeleteDialogComponent{

  constructor(
    protected jobHistoryService: JobHistoryExtService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(jobHistoryService, activeModal, eventManager);
  }

}
