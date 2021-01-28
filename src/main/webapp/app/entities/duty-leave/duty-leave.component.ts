import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IDutyLeave } from 'app/shared/model/duty-leave.model';
import { DutyLeaveService } from './duty-leave.service';
import { AttendanceSummaryService } from 'app/entities/attendance-summary/attendance-summary.service';
import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';

@Component({
  selector: 'jhi-duty-leave',
  templateUrl: './duty-leave.component.html',
})
export class DutyLeaveComponent implements OnInit, OnDestroy {
  dutyLeaves?: IDutyLeave[];
  attendanceSummaries: IAttendanceSummary[] = [];
  eventSubscriber?: Subscription;

  constructor(
    protected dutyLeaveService: DutyLeaveService,
    protected attendanceSummaryService: AttendanceSummaryService,
    protected eventManager: JhiEventManager
  ) {}

  loadAll(): void {
    this.attendanceSummaryService.findWhoWillGetDutyLeave().subscribe((res: HttpResponse<IAttendanceSummary[]>) => {
      this.attendanceSummaries = res.body!;
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDutyLeaves();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendanceSummary): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.serialNo!;
  }

  registerChangeInDutyLeaves(): void {
    this.eventSubscriber = this.eventManager.subscribe('dutyLeaveListModification', () => this.loadAll());
  }
}
