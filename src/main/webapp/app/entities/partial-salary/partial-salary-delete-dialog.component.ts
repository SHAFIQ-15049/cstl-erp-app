import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartialSalary } from 'app/shared/model/partial-salary.model';
import { PartialSalaryService } from './partial-salary.service';

@Component({
  templateUrl: './partial-salary-delete-dialog.component.html',
})
export class PartialSalaryDeleteDialogComponent {
  partialSalary?: IPartialSalary;

  constructor(
    protected partialSalaryService: PartialSalaryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partialSalaryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partialSalaryListModification');
      this.activeModal.close();
    });
  }
}
