import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import {PersonalInfoDetailComponent} from "app/entities/personal-info/personal-info-detail.component";

@Component({
  selector: 'jhi-personal-info-detail',
  templateUrl: './personal-info-ext-detail.component.html',
})
export class PersonalInfoExtDetailComponent extends PersonalInfoDetailComponent implements OnInit {

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }

}
