import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployee } from 'app/shared/model/employee.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EmployeeExtService } from './employee-ext.service';
import { EmployeeExtDeleteDialogComponent } from './employee-ext-delete-dialog.component';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { EmployeeComponent } from 'app/entities/employee/employee.component';

@Component({
  selector: 'jhi-employee',
  templateUrl: './employee-ext.component.html',
})
export class EmployeeExtComponent extends EmployeeComponent implements OnInit, OnDestroy {
  employees?: IEmployee[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected employeeService: EmployeeExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private stateStorageService: StateStorageService
  ) {
    super(employeeService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  registerChangeInEmployees(): void {
    this.eventSubscriber = this.eventManager.subscribe('employeeListModification', () => {
      this.loadPage();
    });
  }

  ngOnInit(): void {
    // storing the url in the session, it will help in return from employee detail to employee list (with url parameters)
    this.stateStorageService.storeCustomUrl(this.router.routerState.snapshot.url);
    this.employeeService.clearEmployeeId();
    this.handleNavigation();
    this.registerChangeInEmployees();
  }
}
