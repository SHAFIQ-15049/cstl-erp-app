import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDesignation } from 'app/shared/model/designation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DesignationService } from './designation.service';
import { DesignationDeleteDialogComponent } from './designation-delete-dialog.component';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { Filter, IFilter } from 'app/shared/model/filter.model';

@Component({
  selector: 'jhi-designation',
  templateUrl: './designation.component.html',
})
export class DesignationComponent implements OnInit, OnDestroy {
  designations?: IDesignation[];
  eventSubscriber?: Subscription;
  employeeCategory!: EmployeeCategory | null;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  param?: any;

  constructor(
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    const filter: IFilter[] = [];
    filter.push(new Filter(page, pageToLoad - 1));
    filter.push(new Filter('size', this.itemsPerPage));
    filter.push(new Filter('sort', this.sort()));
    if (this.employeeCategory) filter.push(new Filter('category.equals', this.employeeCategory));

    let query = {};
    if (this.employeeCategory) {
      query = {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'category.equals': this.employeeCategory,
      };
    } else {
      query = {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
    }

    const filterStr = JSON.stringify(filter);
    this.designationService.query(query).subscribe(
      (res: HttpResponse<IDesignation[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.designationService.getEmployeeCategory().subscribe(e => (this.employeeCategory = e));

    this.handleNavigation();
    this.registerChangeInDesignations();
  }

  fetch(): void {
    this.designationService.setEmployeeCategory(this.employeeCategory);
    this.loadPage();
  }

  fetchAll(): void {
    this.employeeCategory = null;
    this.fetch();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
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
    //this.designationService.setEmployeeCategory(null);
  }

  trackId(index: number, item: IDesignation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDesignations(): void {
    this.eventSubscriber = this.eventManager.subscribe('designationListModification', () => this.loadPage());
  }

  delete(designation: IDesignation): void {
    const modalRef = this.modalService.open(DesignationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.designation = designation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDesignation[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/designation'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.designations = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
