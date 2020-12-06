import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobHistory } from 'app/shared/model/job-history.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JobHistoryExtService } from './job-history-ext.service';
import { JobHistoryExtDeleteDialogComponent } from './job-history-ext-delete-dialog.component';
import {JobHistoryComponent} from "app/entities/job-history/job-history.component";

@Component({
  selector: 'jhi-job-history',
  templateUrl: './job-history-ext.component.html',
})
export class JobHistoryExtComponent extends JobHistoryComponent implements OnInit, OnDestroy {


  constructor(
    protected jobHistoryService: JobHistoryExtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(jobHistoryService, activatedRoute, router, eventManager, modalService);
  }


}
