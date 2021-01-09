import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AttendanceDataUploadComponent } from './attendance-data-upload.component';
import { AttendanceDataUploadDetailComponent } from './attendance-data-upload-detail.component';
import { AttendanceDataUploadUpdateComponent } from './attendance-data-upload-update.component';
import { AttendanceDataUploadDeleteDialogComponent } from './attendance-data-upload-delete-dialog.component';
import { attendanceDataUploadRoute } from './attendance-data-upload.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(attendanceDataUploadRoute)],
  declarations: [
    AttendanceDataUploadComponent,
    AttendanceDataUploadDetailComponent,
    AttendanceDataUploadUpdateComponent,
    AttendanceDataUploadDeleteDialogComponent,
  ],
  entryComponents: [AttendanceDataUploadDeleteDialogComponent],
})
export class CodeNodeErpAttendanceDataUploadModule {}
