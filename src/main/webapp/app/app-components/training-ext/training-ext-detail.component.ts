import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITraining } from 'app/shared/model/training.model';
import {TrainingDetailComponent} from "app/entities/training/training-detail.component";
import {JhiDataUtils} from "ng-jhipster";

@Component({
  selector: 'jhi-training-detail',
  templateUrl: './training-ext-detail.component.html',
})
export class TrainingExtDetailComponent extends TrainingDetailComponent implements OnInit {

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
