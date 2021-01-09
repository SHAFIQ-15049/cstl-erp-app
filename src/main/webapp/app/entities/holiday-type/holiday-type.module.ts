import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { HolidayTypeComponent } from './holiday-type.component';
import { HolidayTypeDetailComponent } from './holiday-type-detail.component';
import { HolidayTypeUpdateComponent } from './holiday-type-update.component';
import { HolidayTypeDeleteDialogComponent } from './holiday-type-delete-dialog.component';
import { holidayTypeRoute } from './holiday-type.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(holidayTypeRoute)],
  declarations: [HolidayTypeComponent, HolidayTypeDetailComponent, HolidayTypeUpdateComponent, HolidayTypeDeleteDialogComponent],
  entryComponents: [HolidayTypeDeleteDialogComponent],
})
export class CodeNodeErpHolidayTypeModule {}
