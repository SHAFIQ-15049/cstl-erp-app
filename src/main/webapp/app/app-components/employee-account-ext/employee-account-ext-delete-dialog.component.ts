import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeAccount } from 'app/shared/model/employee-account.model';
import { EmployeeAccountExtService } from './employee-account-ext.service';
import {EmployeeAccountDeleteDialogComponent} from "app/entities/employee-account/employee-account-delete-dialog.component";

@Component({
  templateUrl: './employee-account-ext-delete-dialog.component.html',
})
export class EmployeeAccountExtDeleteDialogComponent extends EmployeeAccountDeleteDialogComponent{

  constructor(
    protected employeeAccountService: EmployeeAccountExtService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(employeeAccountService, activeModal, eventManager);
  }

}
