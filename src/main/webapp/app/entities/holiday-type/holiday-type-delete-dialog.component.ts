import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';

@Component({
  templateUrl: './holiday-type-delete-dialog.component.html',
})
export class HolidayTypeDeleteDialogComponent {
  holidayType?: IHolidayType;

  constructor(
    protected holidayTypeService: HolidayTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.holidayTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('holidayTypeListModification');
      this.activeModal.close();
    });
  }
}
