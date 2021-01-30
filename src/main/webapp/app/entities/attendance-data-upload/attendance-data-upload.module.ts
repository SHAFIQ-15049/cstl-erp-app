import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AttendanceDataUploadUpdateComponent } from './attendance-data-upload-update.component';
import { attendanceDataUploadRoute } from './attendance-data-upload.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(attendanceDataUploadRoute)],
  declarations: [AttendanceDataUploadUpdateComponent],
  entryComponents: [],
})
export class CodeNodeErpAttendanceDataUploadModule {}
