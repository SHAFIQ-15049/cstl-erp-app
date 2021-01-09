import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';

@Component({
  templateUrl: './holiday-delete-dialog.component.html',
})
export class HolidayDeleteDialogComponent {
  holiday?: IHoliday;

  constructor(protected holidayService: HolidayService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.holidayService.delete(id).subscribe(() => {
      this.eventManager.broadcast('holidayListModification');
      this.activeModal.close();
    });
  }
}
