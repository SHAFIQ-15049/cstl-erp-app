import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { FestivalAllowanceTimeLineComponent } from './festival-allowance-time-line.component';
import { FestivalAllowanceTimeLineDetailComponent } from './festival-allowance-time-line-detail.component';
import { FestivalAllowanceTimeLineUpdateComponent } from './festival-allowance-time-line-update.component';
import { FestivalAllowanceTimeLineDeleteDialogComponent } from './festival-allowance-time-line-delete-dialog.component';
import { festivalAllowanceTimeLineRoute } from './festival-allowance-time-line.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(festivalAllowanceTimeLineRoute)],
  declarations: [
    FestivalAllowanceTimeLineComponent,
    FestivalAllowanceTimeLineDetailComponent,
    FestivalAllowanceTimeLineUpdateComponent,
    FestivalAllowanceTimeLineDeleteDialogComponent,
  ],
  entryComponents: [FestivalAllowanceTimeLineDeleteDialogComponent],
})
export class CodeNodeErpFestivalAllowanceTimeLineModule {}
