import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from './leave-balance.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveBalanceDetailComponent } from 'app/entities/leave-balance/leave-balance-detail.component';
import { IConstant } from 'app/shared/model/constant.model';
import { YEARS } from 'app/shared/constants/common.constants';

@Component({
  selector: 'jhi-leave-balance',
  templateUrl: './leave-balance.component.html',
})
export class LeaveBalanceComponent implements OnInit, OnDestroy {
  leaveBalances?: ILeaveBalance[];
  employees?: IEmployee[];
  employeeId?: number;
  eventSubscriber?: Subscription;

  years: IConstant[] = [];
  selectedYear?: number;

  constructor(
    protected leaveBalanceService: LeaveBalanceService,
    protected employeeService: EmployeeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    if (this.employeeId && this.selectedYear) {
      this.leaveBalanceService
        .findByEmployeeId(this.employeeId, this.selectedYear)
        .subscribe((res: HttpResponse<ILeaveBalance[]>) => (this.leaveBalances = res.body || []));
    }
  }

  ngOnInit(): void {
    this.years = YEARS(2000, new Date().getFullYear());
    this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
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

  showDetail(acceptedLeaveApplications?: ILeaveApplication[]): void {
    const modalRef = this.modalService.open(LeaveBalanceDetailComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leaveApplications = acceptedLeaveApplications;
  }
}
