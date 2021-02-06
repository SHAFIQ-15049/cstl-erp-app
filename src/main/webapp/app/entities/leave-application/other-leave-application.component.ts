import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LeaveApplicationService } from './leave-application.service';
import { LeaveApplicationDeleteDialogComponent } from './leave-application-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'jhi-other-leave-application',
  templateUrl: './other-leave-application.component.html',
})
export class OtherLeaveApplicationComponent implements OnInit, OnDestroy {
  leaveApplications: ILeaveApplication[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  currentUser: Account | null = null;

  fromDate = '';
  toDate = '';
  private dateFormat = 'yyyy-MM-dd';

  constructor(
    protected leaveApplicationService: LeaveApplicationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    private datePipe: DatePipe
  ) {
    this.leaveApplications = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  private firstDayOfTheYear(): string {
    const date = new Date();
    const firstDay = new Date(date.getFullYear(), 0, 1);
    return this.datePipe.transform(firstDay, this.dateFormat)!;
  }

  private lastDayOfTheYear(): string {
    const date = new Date();
    const lastDay = new Date(date.getFullYear(), 11, 31);
    return this.datePipe.transform(lastDay, this.dateFormat)!;
  }

  canLoad(): boolean {
    return this.fromDate !== '' && this.toDate !== '';
  }

  loadAll(): void {
    if (this.canLoad()) {
      this.leaveApplicationService
        .query({
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
          'appliedById.equals': this.currentUser?.id,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe((res: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(res.body, res.headers));
    }
  }

  reset(): void {
    this.page = 0;
    this.leaveApplications = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.toDate = this.lastDayOfTheYear();
    this.fromDate = this.firstDayOfTheYear();
    this.accountService.identity().subscribe(currentUser => {
      this.currentUser = currentUser;
      this.loadAll();
    });
    this.registerChangeInLeaveApplications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILeaveApplication): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeaveApplications(): void {
    this.eventSubscriber = this.eventManager.subscribe('leaveApplicationListModification', () => this.reset());
  }

  delete(leaveApplication: ILeaveApplication): void {
    const modalRef = this.modalService.open(LeaveApplicationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leaveApplication = leaveApplication;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLeaveApplications(data: ILeaveApplication[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.leaveApplications.push(data[i]);
      }
    }
  }
}
