import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompany } from 'app/shared/model/company.model';
import { CompanyDetailComponent } from 'app/entities/company/company-detail.component';

@Component({
  selector: 'jhi-company-extended-detail',
  templateUrl: './company-extended-detail.component.html',
})
export class CompanyExtendedDetailComponent extends CompanyDetailComponent implements OnInit {
  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
