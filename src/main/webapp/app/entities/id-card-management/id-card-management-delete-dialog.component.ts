import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdCardManagement } from 'app/shared/model/id-card-management.model';
import { IdCardManagementService } from './id-card-management.service';

@Component({
  templateUrl: './id-card-management-delete-dialog.component.html',
})
export class IdCardManagementDeleteDialogComponent {
  idCardManagement?: IIdCardManagement;

  constructor(
    protected idCardManagementService: IdCardManagementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.idCardManagementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('idCardManagementListModification');
      this.activeModal.close();
    });
  }
}
