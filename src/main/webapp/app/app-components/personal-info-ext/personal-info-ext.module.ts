import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { PersonalInfoExtComponent } from './personal-info-ext.component';
import { PersonalInfoExtDetailComponent } from './personal-info-ext-detail.component';
import { PersonalInfoExtUpdateComponent } from './personal-info-ext-update.component';
import { PersonalInfoExtDeleteDialogComponent } from './personal-info-ext-delete-dialog.component';
import { personalInfoExtRoute } from './personal-info-ext.route';
import {PersonalInfoComponent} from "app/entities/personal-info/personal-info.component";
import {PersonalInfoDetailComponent} from "app/entities/personal-info/personal-info-detail.component";
import {PersonalInfoUpdateComponent} from "app/entities/personal-info/personal-info-update.component";
import {PersonalInfoDeleteDialogComponent} from "app/entities/personal-info/personal-info-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(personalInfoExtRoute)],
  declarations: [PersonalInfoComponent, PersonalInfoDetailComponent, PersonalInfoUpdateComponent, PersonalInfoDeleteDialogComponent,PersonalInfoExtComponent, PersonalInfoExtDetailComponent, PersonalInfoExtUpdateComponent, PersonalInfoExtDeleteDialogComponent],
  entryComponents: [PersonalInfoExtDeleteDialogComponent],
})
export class CodeNodeErpPersonalInfoModule {}
