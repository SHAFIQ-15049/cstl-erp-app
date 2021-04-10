import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendance } from 'app/shared/model/attendance.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AttendanceService } from './attendance.service';
import { AttendanceDeleteDialogComponent } from './attendance-delete-dialog.component';
import { DatePipe } from '@angular/common';
import { Moment } from 'moment';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

@Component({
  selector: 'jhi-attendance',
  templateUrl: './attendance.component.html',
})
export class AttendanceComponent implements OnInit, OnDestroy {
  attendances?: IAttendance[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  fromDateTime?: string;
  toDateTime?: string;
  empId?: string;

  constructor(
    protected attendanceService: AttendanceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  private static today(): string {
    return moment(new Date()).format('YYYY-MM-DDTHH:mm');
  }

  canLoad(): boolean {
    return this.fromDateTime !== undefined && this.toDateTime !== undefined;
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    if (this.canLoad() && this.empId) {
      const pageToLoad: number = page || this.page || 1;

      this.attendanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'attendanceTime.greaterOrEqualThan': this.fromDateTime + ':00Z',
          'attendanceTime.lessOrEqualThan': this.toDateTime + ':00Z',
          'empId.equals': this.empId,
        })
        .subscribe(
          (res: HttpResponse<IAttendance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.canLoad()) {
      const pageToLoad: number = page || this.page || 1;

      this.attendanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'attendanceTime.greaterOrEqualThan': this.fromDateTime + ':00Z',
          'attendanceTime.lessOrEqualThan': this.toDateTime + ':00Z',
        })
        .subscribe(
          (res: HttpResponse<IAttendance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      const pageToLoad: number = page || this.page || 1;

      this.attendanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IAttendance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.toDateTime = AttendanceComponent.today();
    this.fromDateTime = AttendanceComponent.today();
    this.handleNavigation();
    this.registerChangeInAttendances();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendances(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendanceListModification', () => this.loadPage());
  }

  delete(attendance: IAttendance): void {
    const modalRef = this.modalService.open(AttendanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attendance = attendance;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAttendance[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/attendance'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.attendances = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
