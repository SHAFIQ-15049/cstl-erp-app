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
import { IConstant } from 'app/shared/model/constant.model';
import { HOURS, MINUTES } from 'app/shared/constants/common.constants';

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

  hours: IConstant[] = [];
  minutes: IConstant[] = [];

  private dateFormat = 'yyyy-MM-dd';
  fromDate = '';
  fromHour?: IConstant;
  fromMinute?: IConstant;
  toDate = '';
  toHour?: IConstant;
  toMinute?: IConstant;

  constructor(
    protected attendanceService: AttendanceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private datePipe: DatePipe
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    // yyyy-MM-dd'T'HH:mm:ss.SSSXXX
    if (this.canLoad() && this.fromHour && this.fromMinute && this.toHour && this.toMinute) {
      this.attendanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'attendanceTime.greaterOrEqualThan': `${this.fromDate}T${this.fromHour?.name}:${this.fromMinute?.name}:00.000Z`,
          'attendanceTime.lessOrEqualThan': `${this.toDate}T${this.toHour?.name}:${this.toMinute?.name}:00.000Z`,
        })
        .subscribe(
          (res: HttpResponse<IAttendance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  private today(): string {
    const date = new Date();
    date.setDate(date.getDate());
    return this.datePipe.transform(date, this.dateFormat)!;
  }

  canLoad(): boolean {
    return this.fromDate !== '' && this.toDate !== '';
  }

  ngOnInit(): void {
    this.hours = HOURS;
    this.minutes = MINUTES;
    this.fromDate = this.today();
    this.toDate = this.today();
    this.fromHour = this.hours[0];
    this.toHour = this.hours[this.hours.length - 1];
    this.fromMinute = this.minutes[0];
    this.toMinute = this.minutes[this.minutes.length - 1];
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
