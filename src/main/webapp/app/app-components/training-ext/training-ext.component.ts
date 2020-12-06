import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITraining } from 'app/shared/model/training.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TrainingExtService } from './training-ext.service';
import { TrainingExtDeleteDialogComponent } from './training-ext-delete-dialog.component';
import {TrainingComponent} from "app/entities/training/training.component";

@Component({
  selector: 'jhi-training',
  templateUrl: './training-ext.component.html',
})
export class TrainingExtComponent extends TrainingComponent implements OnInit, OnDestroy {

  constructor(
    protected trainingService: TrainingExtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(trainingService, activatedRoute, router, eventManager, modalService);
  }

}
