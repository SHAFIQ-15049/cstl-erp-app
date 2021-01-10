import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AttendanceDataUploadService } from './attendance-data-upload.service';
import { AttendanceDataUploadDeleteDialogComponent } from './attendance-data-upload-delete-dialog.component';

@Component({
  selector: 'jhi-attendance-data-upload',
  templateUrl: './attendance-data-upload.component.html',
})
export class AttendanceDataUploadComponent implements OnInit, OnDestroy {
  attendanceDataUploads: IAttendanceDataUpload[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected attendanceDataUploadService: AttendanceDataUploadService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.attendanceDataUploads = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.attendanceDataUploadService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IAttendanceDataUpload[]>) => this.paginateAttendanceDataUploads(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.attendanceDataUploads = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttendanceDataUploads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendanceDataUpload): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAttendanceDataUploads(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendanceDataUploadListModification', () => this.reset());
  }

  delete(attendanceDataUpload: IAttendanceDataUpload): void {
    const modalRef = this.modalService.open(AttendanceDataUploadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attendanceDataUpload = attendanceDataUpload;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAttendanceDataUploads(data: IAttendanceDataUpload[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.attendanceDataUploads.push(data[i]);
      }
    }
  }
}
