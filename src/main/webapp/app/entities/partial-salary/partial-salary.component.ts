import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartialSalary } from 'app/shared/model/partial-salary.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PartialSalaryService } from './partial-salary.service';
import { PartialSalaryDeleteDialogComponent } from './partial-salary-delete-dialog.component';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

@Component({
  selector: 'jhi-partial-salary',
  templateUrl: './partial-salary.component.html',
})
export class PartialSalaryComponent implements OnInit, OnDestroy {
  partialSalaries?: IPartialSalary[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  years: number[] = [];
  year!: number;
  month!: MonthType;

  constructor(
    protected partialSalaryService: PartialSalaryService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private jhiAlertService: JhiAlertService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.partialSalaryService
      .query({
        'year.equals': this.year,
        'month.equals': this.month,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IPartialSalary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.configureYears();
    this.handleNavigation();
    this.registerChangeInPartialSalaries();
  }

  fetch(): void {
    if (this.year && this.month) {
      this.router.navigate(['/partial-salary'], { queryParams: { year: this.year, month: this.month }, relativeTo: this.activatedRoute });
    } else {
      this.jhiAlertService.warning('Both year and month are needed to be selected');
    }
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      this.year = +params.get('year')!;
      this.month = MonthType[params.get('month')!];
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if ((pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) && this.year && this.month) {
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

  trackId(index: number, item: IPartialSalary): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPartialSalaries(): void {
    this.eventSubscriber = this.eventManager.subscribe('partialSalaryListModification', () => this.loadPage());
  }

  delete(partialSalary: IPartialSalary): void {
    const modalRef = this.modalService.open(PartialSalaryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partialSalary = partialSalary;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPartialSalary[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/partial-salary'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.partialSalaries = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  configureYears(): void {
    let year = new Date().getFullYear();
    this.year = new Date().getFullYear();
    this.years.push(year);
    for (let i = 0; i < 3; i++) {
      year -= 1;
      this.years.push(year);
    }
  }
}
