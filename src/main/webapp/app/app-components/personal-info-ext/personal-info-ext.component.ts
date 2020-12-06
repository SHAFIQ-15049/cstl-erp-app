import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonalInfo } from 'app/shared/model/personal-info.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PersonalInfoExtService } from './personal-info-ext.service';
import { PersonalInfoExtDeleteDialogComponent } from './personal-info-ext-delete-dialog.component';
import {PersonalInfoComponent} from "app/entities/personal-info/personal-info.component";

@Component({
  selector: 'jhi-personal-info',
  templateUrl: './personal-info-ext.component.html',
})
export class PersonalInfoExtComponent extends PersonalInfoComponent implements OnInit, OnDestroy {

  constructor(
    protected personalInfoService: PersonalInfoExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(personalInfoService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

}
