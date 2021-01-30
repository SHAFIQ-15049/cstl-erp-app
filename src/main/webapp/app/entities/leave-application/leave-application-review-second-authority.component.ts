import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LeaveApplicationService } from './leave-application.service';
import { LeaveApplicationDeleteDialogComponent } from './leave-application-delete-dialog.component';
import { LeaveApplicationStatus } from 'app/shared/model/enumerations/leave-application-status.model';

@Component({
  selector: 'jhi-leave-application-review-second-authority',
  templateUrl: './leave-application-review-second-authority.component.html',
})
export class LeaveApplicationReviewSecondAuthorityComponent implements OnInit, OnDestroy {
  leaveApplications: ILeaveApplication[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected leaveApplicationService: LeaveApplicationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
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

  loadAll(): void {
    this.leaveApplicationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
        'status.equals': LeaveApplicationStatus.ACCEPTED_BY_FIRST_AUTHORITY,
      })
      .subscribe((res: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(res.body, res.headers));
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
    this.loadAll();
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
