import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from './leave-balance.service';

@Component({
  selector: 'jhi-leave-balance',
  templateUrl: './leave-balance.component.html',
})
export class LeaveBalanceComponent implements OnInit, OnDestroy {
  leaveBalances?: ILeaveBalance[];
  eventSubscriber?: Subscription;

  constructor(protected leaveBalanceService: LeaveBalanceService, protected eventManager: JhiEventManager) {}

  loadAll(): void {
    this.leaveBalanceService.query().subscribe((res: HttpResponse<ILeaveBalance[]>) => (this.leaveBalances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLeaveBalances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILeaveBalance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeaveBalances(): void {
    this.eventSubscriber = this.eventManager.subscribe('leaveBalanceListModification', () => this.loadAll());
  }
}
