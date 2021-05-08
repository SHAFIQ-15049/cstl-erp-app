import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOverTime } from 'app/shared/model/over-time.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OverTimeService } from './over-time.service';
import { OverTimeDeleteDialogComponent } from './over-time-delete-dialog.component';
import { IDesignation } from 'app/shared/model/designation.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { Moment } from 'moment';
import * as moment from 'moment';
import { DesignationService } from 'app/entities/designation/designation.service';
import { over } from 'webstomp-client';

@Component({
  selector: 'jhi-over-time',
  templateUrl: './over-time.component.html',
})
export class OverTimeComponent implements OnInit, OnDestroy {
  overTimes?: IOverTime[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  years: number[] = [];
  selectedYear?: number;
  designations: IDesignation[] = [];
  selectedMonth?: MonthType;
  fromDate?: Moment;
  toDate?: Moment;
  pageNumber?: number;
  showLoader = false;

  constructor(
    protected overTimeService: OverTimeService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private alertService: JhiAlertService,
    private designationService: DesignationService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    this.showLoader = true;
    this.overTimeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: ['id,asc'],
        'year.equals': this.selectedYear,
        'month.equals': this.selectedMonth,
      })
      .subscribe(
        (res: HttpResponse<IOverTime[]>) => {
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.configureYears();
    this.handleNavigation();
    this.registerChangeInOverTimes();
  }

  protected handleNavigation(): void {
    combineLatest(
      this.activatedRoute.data,
      this.activatedRoute.queryParamMap,
      this.activatedRoute.params,
      (data: Data, params: ParamMap, aParams) => {
        const page = params.get('page');
        this.pageNumber = page !== null ? +page : 1;
        const sort = (params.get('sort') ?? data['defaultSort']).split(',');
        const predicate = sort[0];
        const ascending = sort[1] === 'asc';
      }
    )
      .pipe(res => {
        return this.activatedRoute.params;
      })
      .subscribe(params => {
        this.selectedYear = +params['selectedYear'];
        this.selectedMonth = params['selectedMonth'];
        if (
          this.pageNumber !== this.page ||
          this.predicate !== this.predicate ||
          this.ascending !== this.ascending ||
          (this.selectedYear && this.selectedMonth)
        ) {
          this.loadPage(this.pageNumber, true);
        }
      });
  }

  navigate(): void {
    if (this.selectedYear && this.selectedMonth) {
      this.router.navigate(['/over-time', this.selectedYear, this.selectedMonth]);
    } else {
      this.alertService.error('Year and month must be selected');
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOverTime): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInOverTimes(): void {
    this.eventSubscriber = this.eventManager.subscribe('overTimeListModification', () => this.loadPage());
  }

  delete(overTime: IOverTime): void {
    const modalRef = this.modalService.open(OverTimeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.overTime = overTime;
  }

  sort(): string[] {
    const result = [this.predicate ? this.predicate : 'id' + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IOverTime[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.showLoader = false;
    if (navigate) {
      this.router.navigate(['/over-time'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.overTimes = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.showLoader = false;
    this.ngbPaginationPage = this.page ?? 1;
  }

  configureYears(): void {
    let year = new Date().getFullYear();
    this.selectedYear = new Date().getFullYear();
    this.years.push(year);
    for (let i = 0; i < 3; i++) {
      year -= 1;
      this.years.push(year);
    }
  }

  generateOverTime(): void {
    this.showLoader = true;
    this.overTimeService.generateOverTimes(this.selectedYear, this.selectedMonth).subscribe(res => {
      this.showLoader = false;
      this.handleNavigation();
    });
  }

  regenerateOverTime(): void {
    this.showLoader = true;
    this.overTimeService.regenerateOverTimes(this.selectedYear, this.selectedMonth).subscribe(res => {
      this.showLoader = false;
      this.alertService.info('Overtime successfully generated');
      this.handleNavigation();
    });
  }

  regenerateEmployeeOverTime(overTime: IOverTime): void {
    this.showLoader = true;
    this.overTimeService.regenerateEmployeeOverTime(overTime.id).subscribe(res => {
      this.showLoader = false;
      this.alertService.info('Employee overtime successfully generated');
      this.handleNavigation();
    });
  }
}
