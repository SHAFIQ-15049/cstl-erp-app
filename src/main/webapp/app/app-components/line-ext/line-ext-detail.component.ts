import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILine } from 'app/shared/model/line.model';
import {LineDetailComponent} from "app/entities/line/line-detail.component";

@Component({
  selector: 'jhi-line-detail',
  templateUrl: './line-ext-detail.component.html',
})
export class LineExtDetailComponent extends LineDetailComponent implements OnInit {

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }

}
