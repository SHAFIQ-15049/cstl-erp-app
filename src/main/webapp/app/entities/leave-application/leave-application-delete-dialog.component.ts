import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';

@Component({
  templateUrl: './leave-application-delete-dialog.component.html',
})
export class LeaveApplicationDeleteDialogComponent {
  leaveApplication?: ILeaveApplication;

  constructor(
    protected leaveApplicationService: LeaveApplicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leaveApplicationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leaveApplicationListModification');
      this.activeModal.close();
    });
  }
}
