import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

@Component({
  selector: 'jhi-attendance-data-upload-detail',
  templateUrl: './attendance-data-upload-detail.component.html',
})
export class AttendanceDataUploadDetailComponent implements OnInit {
  attendanceDataUpload: IAttendanceDataUpload | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendanceDataUpload }) => (this.attendanceDataUpload = attendanceDataUpload));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
