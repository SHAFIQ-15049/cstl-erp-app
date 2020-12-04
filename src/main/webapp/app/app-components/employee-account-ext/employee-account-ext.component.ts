import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployeeAccount } from 'app/shared/model/employee-account.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EmployeeAccountExtService } from './employee-account-ext.service';
import { EmployeeAccountExtDeleteDialogComponent } from './employee-account-ext-delete-dialog.component';
import {EmployeeAccountComponent} from "app/entities/employee-account/employee-account.component";

@Component({
  selector: 'jhi-employee-account',
  templateUrl: './employee-account-ext.component.html',
})
export class EmployeeAccountExtComponent extends EmployeeAccountComponent implements OnInit, OnDestroy {

  constructor(
    protected employeeAccountService: EmployeeAccountExtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(employeeAccountService, activatedRoute, router, eventManager, modalService);
  }

}
