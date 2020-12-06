import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { EducationalInfoExtComponent } from './educational-info-ext.component';
import { EducationalInfoExtDetailComponent } from './educational-info-ext-detail.component';
import { EducationalInfoExtUpdateComponent } from './educational-info-ext-update.component';
import { EducationalInfoExtDeleteDialogComponent } from './educational-info-ext-delete-dialog.component';
import { educationalInfoExtRoute } from './educational-info-ext.route';
import {EducationalInfoComponent} from "app/entities/educational-info/educational-info.component";
import {EducationalInfoDetailComponent} from "app/entities/educational-info/educational-info-detail.component";
import {EducationalInfoUpdateComponent} from "app/entities/educational-info/educational-info-update.component";
import {EducationalInfoDeleteDialogComponent} from "app/entities/educational-info/educational-info-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(educationalInfoExtRoute)],
  declarations: [
    EducationalInfoComponent,
    EducationalInfoDetailComponent,
    EducationalInfoUpdateComponent,
    EducationalInfoDeleteDialogComponent,
    EducationalInfoExtComponent,
    EducationalInfoExtDetailComponent,
    EducationalInfoExtUpdateComponent,
    EducationalInfoExtDeleteDialogComponent,
  ],
  entryComponents: [EducationalInfoExtDeleteDialogComponent],
})
export class CodeNodeErpEducationalInfoModule {}
