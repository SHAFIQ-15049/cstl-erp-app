import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';
import { FestivalAllowanceTimeLineService } from './festival-allowance-time-line.service';

@Component({
  templateUrl: './festival-allowance-time-line-delete-dialog.component.html',
})
export class FestivalAllowanceTimeLineDeleteDialogComponent {
  festivalAllowanceTimeLine?: IFestivalAllowanceTimeLine;

  constructor(
    protected festivalAllowanceTimeLineService: FestivalAllowanceTimeLineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.festivalAllowanceTimeLineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('festivalAllowanceTimeLineListModification');
      this.activeModal.close();
    });
  }
}
