import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FestivalAllowancePaymentDtlService } from './festival-allowance-payment-dtl.service';
import { FestivalAllowancePaymentDtlDeleteDialogComponent } from './festival-allowance-payment-dtl-delete-dialog.component';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';

@Component({
  selector: 'jhi-festival-allowance-payment-dtl',
  templateUrl: './festival-allowance-payment-dtl.component.html',
})
export class FestivalAllowancePaymentDtlComponent implements OnInit, OnDestroy {
  festivalAllowancePaymentDtls?: IFestivalAllowancePaymentDtl[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  festivalAllowanceId?: number;

  constructor(
    protected festivalAllowancePaymentDtlService: FestivalAllowancePaymentDtlService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private festivalAllowanceService: FestivalAllowancePaymentService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.festivalAllowancePaymentDtlService
      .query({
        'festivalAllowancePaymentId.equals': this.festivalAllowanceService.getFestivalAllowanceId(),
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IFestivalAllowancePaymentDtl[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInFestivalAllowancePaymentDtls();
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
  }

  trackId(index: number, item: IFestivalAllowancePaymentDtl): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInFestivalAllowancePaymentDtls(): void {
    this.eventSubscriber = this.eventManager.subscribe('festivalAllowancePaymentDtlListModification', () => this.loadPage());
  }

  delete(festivalAllowancePaymentDtl: IFestivalAllowancePaymentDtl): void {
    const modalRef = this.modalService.open(FestivalAllowancePaymentDtlDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.festivalAllowancePaymentDtl = festivalAllowancePaymentDtl;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IFestivalAllowancePaymentDtl[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/festival-allowance-payment-dtl'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.festivalAllowancePaymentDtls = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
