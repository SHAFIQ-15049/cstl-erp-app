import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEducationalInfo } from 'app/shared/model/educational-info.model';
import {EducationalInfoDetailComponent} from "app/entities/educational-info/educational-info-detail.component";

@Component({
  selector: 'jhi-educational-info-detail',
  templateUrl: './educational-info-ext-detail.component.html',
})
export class EducationalInfoExtDetailComponent extends EducationalInfoDetailComponent implements OnInit {

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }

}
