import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdCardManagement } from 'app/shared/model/id-card-management.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IdCardManagementService } from './id-card-management.service';
import { IdCardManagementDeleteDialogComponent } from './id-card-management-delete-dialog.component';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { EmployeeExtService } from 'app/app-components/employee-ext/employee-ext.service';

@Component({
  selector: 'jhi-id-card-management',
  templateUrl: './id-card-management.component.html',
})
export class IdCardManagementComponent implements OnInit, OnDestroy {
  idCardManagements?: IIdCardManagement[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  employeeId!: number;

  constructor(
    protected idCardManagementService: IdCardManagementService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected employeeService: EmployeeExtService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.idCardManagementService
      .query({
        'employeeId.equals': this.employeeId,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IIdCardManagement[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInIdCardManagements();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      this.employeeId = +this.activatedRoute.snapshot.params['employeeId'];
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIdCardManagement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIdCardManagements(): void {
    this.eventSubscriber = this.eventManager.subscribe('idCardManagementListModification', () => this.loadPage());
  }

  delete(idCardManagement: IIdCardManagement): void {
    const modalRef = this.modalService.open(IdCardManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.idCardManagement = idCardManagement;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIdCardManagement[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['../../id-card-management', this.employeeId], {
        relativeTo: this.activatedRoute, //this is a must
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.idCardManagements = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  public downloadIdCard(id: number): void {
    this.employeeService.downloadIdCard(id).subscribe(data => {
      const file = new Blob([data], { type: 'application/octet-stream' });
      const pdfData = URL.createObjectURL(file);
      const link = document.createElement('a');
      link.href = pdfData;
      link.download = 'id-card.xls';
      link.click();
    });
  }
}
