import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekendDateMap } from 'app/shared/model/weekend-date-map.model';
import { WeekendDateMapService } from './weekend-date-map.service';

import { MONTHS, YEARS } from 'app/shared/constants/common.constants';
import { IConstant } from 'app/shared/model/constant.model';

@Component({
  selector: 'jhi-weekend-date-map',
  templateUrl: './weekend-date-map.component.html',
})
export class WeekendDateMapComponent implements OnInit, OnDestroy {
  weekendDateMaps: IWeekendDateMap[];
  eventSubscriber?: Subscription;

  years: IConstant[] = [];
  selectedYear?: IConstant;

  months: IConstant[] = [];
  selectedMonth?: IConstant;

  offset = 5;
  fromYear = 2015;
  lastYear = new Date().getFullYear() + this.offset;

  constructor(protected weekendDateMapService: WeekendDateMapService, protected eventManager: JhiEventManager) {
    this.weekendDateMaps = [];
  }

  loadAll(): void {
    if (this.selectedYear && this.selectedMonth && this.selectedYear.id && this.selectedMonth.id) {
      this.weekendDateMapService
        .fetchByYearAndMonth(this.selectedYear.id, this.selectedMonth.id)
        .subscribe((res: HttpResponse<IWeekendDateMap[]>) => (this.weekendDateMaps = res.body || []));
    } else if (this.selectedYear && this.selectedYear.id) {
      this.weekendDateMapService
        .fetchByYear(this.selectedYear.id)
        .subscribe((res: HttpResponse<IWeekendDateMap[]>) => (this.weekendDateMaps = res.body || []));
    } else {
      this.weekendDateMapService.query().subscribe((res: HttpResponse<IWeekendDateMap[]>) => (this.weekendDateMaps = res.body || []));
    }
  }

  reset(): void {
    this.weekendDateMaps = [];
    this.loadAll();
  }

  ngOnInit(): void {
    this.years = YEARS(this.fromYear, this.lastYear);
    this.months = MONTHS;
    this.selectedYear = this.years[this.lastYear - this.fromYear - this.offset];
    this.selectedMonth = this.months[0];
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
}
