import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FestivalAllowancePaymentService } from './festival-allowance-payment.service';
import { FestivalAllowancePaymentDeleteDialogComponent } from './festival-allowance-payment-delete-dialog.component';
import { IDesignation } from 'app/shared/model/designation.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-festival-allowance-payment',
  templateUrl: './festival-allowance-payment.component.html',
})
export class FestivalAllowancePaymentComponent implements OnInit, OnDestroy {
  festivalAllowancePayments?: IFestivalAllowancePayment[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  years: number[] = [];
  year?: number;
  designations: IDesignation[] = [];
  designationId?: number;
  month?: MonthType;

  constructor(
    protected festivalAllowancePaymentService: FestivalAllowancePaymentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected jhiAlertService: JhiAlertService,
    protected designationService: DesignationService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.year && this.month && this.designationId) {
      this.festivalAllowancePaymentService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'year.equals': this.year,
          'month.equals': this.month,
          'designationId.equals': this.designationId,
        })
        .subscribe(
          (res: HttpResponse<IFestivalAllowancePayment[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.configureYears();
    this.handleNavigation();
    this.registerChangeInFestivalAllowancePayments();
    this.designationService
      .query({
        size: 10000,
      })
      .subscribe(res => {
        this.designations = res.body || [];
      });
  }

  protected handleNavigation(): void {
    combineLatest(
      this.activatedRoute.data,
      this.activatedRoute.queryParamMap,
      this.activatedRoute.params,
      (data: Data, params: ParamMap, linkParams: any) => {
        const page = params.get('page');
        const pageNumber = page !== null ? +page : 1;
        const sort = (params.get('sort') ?? data['defaultSort']).split(',');
        const predicate = sort[0];
        const ascending = sort[1] === 'asc';

        this.year = +linkParams['year'];
        this.month = linkParams['month'];
        this.designationId = +linkParams['designationId'];
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    ).subscribe();
  }

  navigate(): void {
    if (this.year && this.month && this.designationId) {
      this.router.navigate(['/festival-allowance-payment', this.year, this.month, this.designationId]);
    } else {
      this.jhiAlertService.warning('Year, month and designation must be selected');
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFestivalAllowancePayment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFestivalAllowancePayments(): void {
    this.eventSubscriber = this.eventManager.subscribe('festivalAllowancePaymentListModification', () => this.loadPage());
  }

  delete(festivalAllowancePayment: IFestivalAllowancePayment): void {
    const modalRef = this.modalService.open(FestivalAllowancePaymentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.festivalAllowancePayment = festivalAllowancePayment;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IFestivalAllowancePayment[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/festival-allowance-payment'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.festivalAllowancePayments = data || [];
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
