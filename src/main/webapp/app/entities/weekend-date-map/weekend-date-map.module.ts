import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { WeekendDateMapComponent } from './weekend-date-map.component';
import { weekendDateMapRoute } from './weekend-date-map.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(weekendDateMapRoute)],
  declarations: [WeekendDateMapComponent],
})
export class CodeNodeErpWeekendDateMapModule {}
