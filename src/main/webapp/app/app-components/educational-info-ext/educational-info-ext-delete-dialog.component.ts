import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEducationalInfo } from 'app/shared/model/educational-info.model';
import { EducationalInfoExtService } from './educational-info-ext.service';
import {EducationalInfoDeleteDialogComponent} from "app/entities/educational-info/educational-info-delete-dialog.component";

@Component({
  templateUrl: './educational-info-ext-delete-dialog.component.html',
})
export class EducationalInfoExtDeleteDialogComponent extends EducationalInfoDeleteDialogComponent{

  constructor(
    protected educationalInfoService: EducationalInfoExtService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(educationalInfoService, activeModal, eventManager);
  }

}
