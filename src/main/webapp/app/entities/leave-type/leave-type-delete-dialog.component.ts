import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from './leave-type.service';

@Component({
  templateUrl: './leave-type-delete-dialog.component.html',
})
export class LeaveTypeDeleteDialogComponent {
  leaveType?: ILeaveType;

  constructor(protected leaveTypeService: LeaveTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leaveTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leaveTypeListModification');
      this.activeModal.close();
    });
  }
}
