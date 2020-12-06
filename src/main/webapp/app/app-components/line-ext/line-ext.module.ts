import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { LineExtComponent } from './line-ext.component';
import { LineExtDetailComponent } from './line-ext-detail.component';
import { LineExtUpdateComponent } from './line-ext-update.component';
import { LineExtDeleteDialogComponent } from './line-ext-delete-dialog.component';
import { lineExtRoute } from './line-ext.route';
import {LineComponent} from "app/entities/line/line.component";
import {LineDetailComponent} from "app/entities/line/line-detail.component";
import {LineUpdateComponent} from "app/entities/line/line-update.component";
import {LineDeleteDialogComponent} from "app/entities/line/line-delete-dialog.component";

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(lineExtRoute)],
  declarations: [LineComponent, LineDetailComponent, LineUpdateComponent, LineDeleteDialogComponent, LineExtComponent, LineExtDetailComponent, LineExtUpdateComponent, LineExtDeleteDialogComponent],
  entryComponents: [LineExtDeleteDialogComponent],
})
export class CodeNodeErpLineModule {}
