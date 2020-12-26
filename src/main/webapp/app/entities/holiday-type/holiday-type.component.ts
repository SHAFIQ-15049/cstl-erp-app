import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHolidayType } from 'app/shared/model/holiday-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HolidayTypeService } from './holiday-type.service';
import { HolidayTypeDeleteDialogComponent } from './holiday-type-delete-dialog.component';

@Component({
  selector: 'jhi-holiday-type',
  templateUrl: './holiday-type.component.html',
})
export class HolidayTypeComponent implements OnInit, OnDestroy {
  holidayTypes: IHolidayType[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected holidayTypeService: HolidayTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.holidayTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.holidayTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IHolidayType[]>) => this.paginateHolidayTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.holidayTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHolidayTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHolidayType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHolidayTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('holidayTypeListModification', () => this.reset());
  }

  delete(holidayType: IHolidayType): void {
    const modalRef = this.modalService.open(HolidayTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.holidayType = holidayType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHolidayTypes(data: IHolidayType[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.holidayTypes.push(data[i]);
      }
    }
  }
}
