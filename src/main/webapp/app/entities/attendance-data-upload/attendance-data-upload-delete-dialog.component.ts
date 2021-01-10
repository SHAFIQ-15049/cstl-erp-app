import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { AttendanceDataUploadService } from './attendance-data-upload.service';

@Component({
  templateUrl: './attendance-data-upload-delete-dialog.component.html',
})
export class AttendanceDataUploadDeleteDialogComponent {
  attendanceDataUpload?: IAttendanceDataUpload;

  constructor(
    protected attendanceDataUploadService: AttendanceDataUploadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendanceDataUploadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendanceDataUploadListModification');
      this.activeModal.close();
    });
  }
}
