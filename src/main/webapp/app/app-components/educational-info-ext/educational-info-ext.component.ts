import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEducationalInfo } from 'app/shared/model/educational-info.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EducationalInfoExtService } from './educational-info-ext.service';
import { EducationalInfoExtDeleteDialogComponent } from './educational-info-ext-delete-dialog.component';
import {EducationalInfoComponent} from "app/entities/educational-info/educational-info.component";

@Component({
  selector: 'jhi-educational-info',
  templateUrl: './educational-info-ext.component.html',
})
export class EducationalInfoExtComponent extends EducationalInfoComponent implements OnInit, OnDestroy {


  constructor(
    protected educationalInfoService: EducationalInfoExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(educationalInfoService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

}
