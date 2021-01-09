import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeaveType } from 'app/shared/model/leave-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LeaveTypeService } from './leave-type.service';
import { LeaveTypeDeleteDialogComponent } from './leave-type-delete-dialog.component';

@Component({
  selector: 'jhi-leave-type',
  templateUrl: './leave-type.component.html',
})
export class LeaveTypeComponent implements OnInit, OnDestroy {
  leaveTypes: ILeaveType[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected leaveTypeService: LeaveTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.leaveTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.leaveTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ILeaveType[]>) => this.paginateLeaveTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.leaveTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLeaveTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILeaveType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeaveTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('leaveTypeListModification', () => this.reset());
  }

  delete(leaveType: ILeaveType): void {
    const modalRef = this.modalService.open(LeaveTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leaveType = leaveType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLeaveTypes(data: ILeaveType[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.leaveTypes.push(data[i]);
      }
    }
  }
}
