import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompany, Company } from 'app/shared/model/company.model';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { CompanyUpdateComponent } from 'app/entities/company/company-update.component';
import { CompanyExtendedService } from 'app/app-components/company-extended/company-extended.service';

@Component({
  selector: 'jhi-company-extended-update',
  templateUrl: './company-extended-update.component.html',
})
export class CompanyExtendedUpdateComponent extends CompanyUpdateComponent implements OnInit {
  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected companyService: CompanyExtendedService,
    protected activatedRoute: ActivatedRoute,
    fb: FormBuilder
  ) {
    super(dataUtils, eventManager, companyService, activatedRoute, fb);
  }
}
