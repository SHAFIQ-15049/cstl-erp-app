import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoliday } from 'app/shared/model/holiday.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HolidayService } from './holiday.service';
import { HolidayDeleteDialogComponent } from './holiday-delete-dialog.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'jhi-holiday',
  templateUrl: './holiday.component.html',
})
export class HolidayComponent implements OnInit, OnDestroy {
  holidays: IHoliday[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  fromDate = '';
  toDate = '';

  private dateFormat = 'yyyy-MM-dd';

  constructor(
    protected holidayService: HolidayService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    private datePipe: DatePipe
  ) {
    this.holidays = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  private getFirstDayOfYear(): string {
    const date = new Date();
    date.setDate(date.getDate());
    return this.datePipe.transform(new Date(date.getFullYear(), 0, 1), this.dateFormat)!;
  }

  private getLastDayOfYear(): string {
    const date = new Date();
    date.setDate(date.getDate());
    return this.datePipe.transform(new Date(date.getFullYear(), 11, 31), this.dateFormat)!;
  }

  loadAll(): void {
    if (this.canLoad()) {
      this.holidayService
        .query({
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe((res: HttpResponse<IHoliday[]>) => this.paginateHolidays(res.body, res.headers));
    }
  }

  reset(): void {
    this.page = 0;
    this.holidays = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  canLoad(): boolean {
    return this.fromDate !== '' && this.toDate !== '';
  }

  ngOnInit(): void {
    this.fromDate = this.getFirstDayOfYear();
    this.toDate = this.getLastDayOfYear();
    this.loadAll();
    this.registerChangeInHolidays();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHoliday): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHolidays(): void {
    this.eventSubscriber = this.eventManager.subscribe('holidayListModification', () => this.reset());
  }

  delete(holiday: IHoliday): void {
    const modalRef = this.modalService.open(HolidayDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.holiday = holiday;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHolidays(data: IHoliday[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.holidays.push(data[i]);
      }
    }
  }
}
