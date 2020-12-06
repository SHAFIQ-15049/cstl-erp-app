import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeExtService } from './employee-ext.service';
import {EmployeeDeleteDialogComponent} from "app/entities/employee/employee-delete-dialog.component";

@Component({
  templateUrl: './employee-ext-delete-dialog.component.html',
})
export class EmployeeExtDeleteDialogComponent extends EmployeeDeleteDialogComponent{
  employee?: IEmployee;

  constructor(protected employeeService: EmployeeExtService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {
    super(employeeService, activeModal, eventManager);
  }

}
