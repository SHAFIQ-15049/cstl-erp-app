import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDefaultAllowance } from 'app/shared/model/default-allowance.model';
import { DefaultAllowanceService } from './default-allowance.service';

@Component({
  templateUrl: './default-allowance-delete-dialog.component.html',
})
export class DefaultAllowanceDeleteDialogComponent {
  defaultAllowance?: IDefaultAllowance;

  constructor(
    protected defaultAllowanceService: DefaultAllowanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.defaultAllowanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('defaultAllowanceListModification');
      this.activeModal.close();
    });
  }
}
