import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IWeekendDateMap } from 'app/shared/model/weekend-date-map.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { WeekendDateMapService } from './weekend-date-map.service';

@Component({
  selector: 'jhi-weekend-date-map',
  templateUrl: './weekend-date-map.component.html',
})
export class WeekendDateMapComponent implements OnInit, OnDestroy {
  weekendDateMaps: IWeekendDateMap[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected weekendDateMapService: WeekendDateMapService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks
  ) {
    this.weekendDateMaps = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'serialNo';
    this.ascending = true;
  }

  loadAll(): void {
    this.weekendDateMapService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IWeekendDateMap[]>) => this.paginateWeekendDateMaps(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.weekendDateMaps = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWeekendDateMaps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWeekendDateMap): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.serialNo!;
  }

  registerChangeInWeekendDateMaps(): void {
    this.eventSubscriber = this.eventManager.subscribe('weekendDateMapListModification', () => this.reset());
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'serialNo') {
      result.push('serialNo');
    }
    return result;
  }

  protected paginateWeekendDateMaps(data: IWeekendDateMap[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.weekendDateMaps.push(data[i]);
      }
    }
  }
}
