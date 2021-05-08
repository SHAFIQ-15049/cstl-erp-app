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
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { ILine } from 'app/shared/model/line.model';
import { Filter, IFilter } from 'app/shared/model/filter.model';
import { IDesignation } from 'app/shared/model/designation.model';

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

  departmentId!: number | null | undefined;
  empId!: string | null | undefined;
  departments?: IDepartment[] = [];

  constructor(
    protected employeeService: EmployeeExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private stateStorageService: StateStorageService,
    protected departmentService: DepartmentService
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

    this.activatedRoute.data.subscribe(() => {
      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  fetchByDept(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    const filter: IFilter[] = [];
    filter.push(new Filter(page, pageToLoad - 1));
    filter.push(new Filter('size', this.itemsPerPage));
    filter.push(new Filter('sort', this.sort()));
    if (this.departmentId) filter.push(new Filter('departmentId.equals', this.departmentId));

    let query = {};
    if (this.departmentId) {
      query = {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'departmentId.equals': this.departmentId,
      };
    } else {
      query = {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
    }

    const filterStr = JSON.stringify(filter);
    this.employeeService.query(query).subscribe(
      (res: HttpResponse<IEmployee[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  fetch(page?: number, dontNavigate?: boolean): void {
    //this.loadPageUsingFilter(this.departmentId,this.empId);

    const pageToLoad: number = page || this.page || 1;

    if (this.empId) {
      this.employeeService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'empId.equals': this.empId,
        })
        .subscribe(
          (res: HttpResponse<IEmployee[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.departmentId) {
      this.employeeService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.departmentId,
        })
        .subscribe(
          (res: HttpResponse<IEmployee[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.employeeService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IEmployee[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  fetchAll(): void {
    this.departmentId = null;
    this.empId = null;
    this.loadPage();
  }
}
