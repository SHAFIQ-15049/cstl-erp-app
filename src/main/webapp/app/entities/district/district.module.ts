import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { DistrictComponent } from './district.component';
import { DistrictDetailComponent } from './district-detail.component';
import { DistrictUpdateComponent } from './district-update.component';
import { DistrictDeleteDialogComponent } from './district-delete-dialog.component';
import { districtRoute } from './district.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(districtRoute)],
  declarations: [DistrictComponent, DistrictDetailComponent, DistrictUpdateComponent, DistrictDeleteDialogComponent],
  entryComponents: [DistrictDeleteDialogComponent],
})
export class CodeNodeErpDistrictModule {}
