import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILine } from 'app/shared/model/line.model';
import { LineExtService } from './line-ext.service';
import {LineDeleteDialogComponent} from "app/entities/line/line-delete-dialog.component";

@Component({
  templateUrl: './line-ext-delete-dialog.component.html',
})
export class LineExtDeleteDialogComponent extends LineDeleteDialogComponent{

  constructor(protected lineService: LineExtService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {
    super(lineService, activeModal, eventManager);
  }


}
