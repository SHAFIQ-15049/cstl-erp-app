import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';

@Component({
  templateUrl: './district-delete-dialog.component.html',
})
export class DistrictDeleteDialogComponent {
  district?: IDistrict;

  constructor(protected districtService: DistrictService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.districtService.delete(id).subscribe(() => {
      this.eventManager.broadcast('districtListModification');
      this.activeModal.close();
    });
  }
}
