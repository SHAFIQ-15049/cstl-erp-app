import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoExtService } from './personal-info-ext.service';
import {PersonalInfoDeleteDialogComponent} from "app/entities/personal-info/personal-info-delete-dialog.component";

@Component({
  templateUrl: './personal-info-ext-delete-dialog.component.html',
})
export class PersonalInfoExtDeleteDialogComponent extends PersonalInfoDeleteDialogComponent{

  constructor(
    protected personalInfoService: PersonalInfoExtService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(personalInfoService, activeModal, eventManager);
  }

}
