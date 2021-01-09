import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from './monthly-salary.service';

@Component({
  templateUrl: './monthly-salary-delete-dialog.component.html',
})
export class MonthlySalaryDeleteDialogComponent {
  monthlySalary?: IMonthlySalary;

  constructor(
    protected monthlySalaryService: MonthlySalaryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.monthlySalaryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('monthlySalaryListModification');
      this.activeModal.close();
    });
  }
}
