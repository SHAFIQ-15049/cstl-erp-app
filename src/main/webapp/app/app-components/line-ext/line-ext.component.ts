import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILine } from 'app/shared/model/line.model';
import { LineExtService } from './line-ext.service';
import { LineExtDeleteDialogComponent } from './line-ext-delete-dialog.component';
import {LineComponent} from "app/entities/line/line.component";
import {LineService} from "app/entities/line/line.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'jhi-line',
  templateUrl: './line-ext.component.html',
})
export class LineExtComponent extends LineComponent implements OnInit, OnDestroy {
  lines?: ILine[];
  eventSubscriber?: Subscription;

  constructor(
    protected lineService: LineService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(lineService, activatedRoute, dataUtils, router, eventManager, modalService);
  }


}
