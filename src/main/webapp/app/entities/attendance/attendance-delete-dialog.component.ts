import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';

@Component({
  templateUrl: './attendance-delete-dialog.component.html',
})
export class AttendanceDeleteDialogComponent {
  attendance?: IAttendance;

  constructor(
    protected attendanceService: AttendanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendanceListModification');
      this.activeModal.close();
    });
  }
}
