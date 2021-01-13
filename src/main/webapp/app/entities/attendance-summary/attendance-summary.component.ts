import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { AttendanceSummaryService } from './attendance-summary.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'jhi-attendance-summary',
  templateUrl: './attendance-summary.component.html',
})
export class AttendanceSummaryComponent implements OnInit, OnDestroy {
  attendanceSummaries?: IAttendanceSummary[];
  eventSubscriber?: Subscription;

  fromDate = '';
  toDate = '';

  private dateFormat = 'yyyy-MM-dd';

  constructor(
    protected attendanceSummaryService: AttendanceSummaryService,
    protected eventManager: JhiEventManager,
    private datePipe: DatePipe
  ) {}

  private today(): string {
    const date = new Date();
    date.setDate(date.getDate());
    return this.datePipe.transform(date, this.dateFormat)!;
  }

  canLoad(): boolean {
    return this.fromDate !== '' && this.toDate !== '';
  }

  loadAll(): void {
    if (this.canLoad()) {
      this.attendanceSummaryService
        .findByFromAndToDate(this.fromDate, this.toDate)
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    }
  }

  ngOnInit(): void {
    this.toDate = this.today();
    this.fromDate = this.today();
    this.loadAll();
    this.registerChangeInAttendanceSummaries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendanceSummary): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendanceSummaries(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendanceSummaryListModification', () => this.loadAll());
  }
}
