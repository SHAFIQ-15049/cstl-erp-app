import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeAccount } from 'app/shared/model/employee-account.model';
import { EmployeeAccountService } from './employee-account.service';

@Component({
  templateUrl: './employee-account-delete-dialog.component.html',
})
export class EmployeeAccountDeleteDialogComponent {
  employeeAccount?: IEmployeeAccount;

  constructor(
    protected employeeAccountService: EmployeeAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeeAccountListModification');
      this.activeModal.close();
    });
  }
}
