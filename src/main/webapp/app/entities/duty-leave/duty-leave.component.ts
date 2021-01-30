import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IDutyLeave } from 'app/shared/model/duty-leave.model';
import { DutyLeaveService } from './duty-leave.service';
import { AttendanceSummaryService } from 'app/entities/attendance-summary/attendance-summary.service';
import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';

@Component({
  selector: 'jhi-duty-leave',
  templateUrl: './duty-leave.component.html',
})
export class DutyLeaveComponent implements OnInit, OnDestroy {
  dutyLeaves?: IDutyLeave[];
  attendanceSummaries: IAttendanceSummary[] = [];
  eventSubscriber?: Subscription;

  isSaving = false;
  fromDate = '';
  toDate = '';

  private dateFormat = 'yyyy-MM-dd';

  constructor(
    protected dutyLeaveService: DutyLeaveService,
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
    this.attendanceSummaryService.findWhoWillGetDutyLeave().subscribe((res: HttpResponse<IAttendanceSummary[]>) => {
      this.attendanceSummaries = res.body!;
    });
  }

  ngOnInit(): void {
    this.toDate = this.today();
    this.fromDate = this.today();
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

  applyLeave(): void {
    if (this.attendanceSummaries) {
      this.isSaving = true;
      const dutyLeaves: IDutyLeave[] = [];
      for (let i = 0; i < this.attendanceSummaries.length; i++) {
        if (this.attendanceSummaries[i].leaveApplied === 'YES') {
          const dutyLeave: IDutyLeave = {};
          dutyLeave.employeeId = this.attendanceSummaries[i].employeeId;
          dutyLeave.fromDate = moment(this.fromDate);
          dutyLeave.toDate = moment(this.toDate);
          dutyLeave.attendanceSummaryDTO = this.attendanceSummaries[i];

          dutyLeaves.push(dutyLeave);
        }
      }
      this.subscribeToSaveResponse(this.dutyLeaveService.update(dutyLeaves));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDutyLeave[]>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.loadAll();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
