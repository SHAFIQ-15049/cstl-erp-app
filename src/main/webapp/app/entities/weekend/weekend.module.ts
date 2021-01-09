import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { WeekendComponent } from './weekend.component';
import { WeekendDetailComponent } from './weekend-detail.component';
import { WeekendUpdateComponent } from './weekend-update.component';
import { WeekendDeleteDialogComponent } from './weekend-delete-dialog.component';
import { weekendRoute } from './weekend.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(weekendRoute)],
  declarations: [WeekendComponent, WeekendDetailComponent, WeekendUpdateComponent, WeekendDeleteDialogComponent],
  entryComponents: [WeekendDeleteDialogComponent],
})
export class CodeNodeErpWeekendModule {}
