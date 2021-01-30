import { Component } from '@angular/core';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-leave-balance-detail',
  templateUrl: './leave-balance-detail.component.html',
})
export class LeaveBalanceDetailComponent {
  leaveApplications?: ILeaveApplication[];

  constructor(public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  trackId(index: number, item: ILeaveApplication): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }
}
