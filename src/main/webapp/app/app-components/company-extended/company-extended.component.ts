import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompany } from 'app/shared/model/company.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CompanyComponent } from 'app/entities/company/company.component';
import { CompanyExtendedService } from 'app/app-components/company-extended/company-extended.service';

@Component({
  selector: 'jhi-company-extended',
  templateUrl: './company-extended.component.html',
})
export class CompanyExtendedComponent extends CompanyComponent implements OnInit, OnDestroy {
  constructor(
    protected companyService: CompanyExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(companyService, activatedRoute, dataUtils, router, eventManager, modalService);
  }
}
