import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceHistory } from 'app/shared/model/service-history.model';
import {ServiceHistoryDetailComponent} from "app/entities/service-history/service-history-detail.component";
import {JhiDataUtils} from "ng-jhipster";

@Component({
  selector: 'jhi-service-history-detail',
  templateUrl: './service-history-ext-detail.component.html',
})
export class ServiceHistoryExtDetailComponent extends ServiceHistoryDetailComponent implements OnInit {

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
