import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEducationalInfo } from 'app/shared/model/educational-info.model';
import { EducationalInfoService } from './educational-info.service';

@Component({
  templateUrl: './educational-info-delete-dialog.component.html',
})
export class EducationalInfoDeleteDialogComponent {
  educationalInfo?: IEducationalInfo;

  constructor(
    protected educationalInfoService: EducationalInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.educationalInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('educationalInfoListModification');
      this.activeModal.close();
    });
  }
}
