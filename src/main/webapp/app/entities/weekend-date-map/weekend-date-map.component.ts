import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekendDateMap } from 'app/shared/model/weekend-date-map.model';
import { WeekendDateMapService } from './weekend-date-map.service';

@Component({
  selector: 'jhi-weekend-date-map',
  templateUrl: './weekend-date-map.component.html',
})
export class WeekendDateMapComponent implements OnInit, OnDestroy {
  weekendDateMaps: IWeekendDateMap[];
  eventSubscriber?: Subscription;

  constructor(protected weekendDateMapService: WeekendDateMapService, protected eventManager: JhiEventManager) {
    this.weekendDateMaps = [];
  }

  loadAll(): void {
    this.weekendDateMapService.query().subscribe((res: HttpResponse<IWeekendDateMap[]>) => (this.weekendDateMaps = res.body || []));
  }

  reset(): void {
    this.weekendDateMaps = [];
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
}
