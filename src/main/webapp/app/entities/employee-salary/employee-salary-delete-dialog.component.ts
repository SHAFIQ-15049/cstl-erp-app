import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';

@Component({
  templateUrl: './employee-salary-delete-dialog.component.html',
})
export class EmployeeSalaryDeleteDialogComponent {
  employeeSalary?: IEmployeeSalary;

  constructor(
    protected employeeSalaryService: EmployeeSalaryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeSalaryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeeSalaryListModification');
      this.activeModal.close();
    });
  }
}
