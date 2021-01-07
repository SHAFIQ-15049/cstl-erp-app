import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { MonthlySalaryDtlService } from './monthly-salary-dtl.service';

@Component({
  templateUrl: './monthly-salary-dtl-delete-dialog.component.html',
})
export class MonthlySalaryDtlDeleteDialogComponent {
  monthlySalaryDtl?: IMonthlySalaryDtl;

  constructor(
    protected monthlySalaryDtlService: MonthlySalaryDtlService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.monthlySalaryDtlService.delete(id).subscribe(() => {
      this.eventManager.broadcast('monthlySalaryDtlListModification');
      this.activeModal.close();
    });
  }
}
