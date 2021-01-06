import { Injectable } from '@angular/core';
import { Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { AttendanceDataUpload, IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { AttendanceDataUploadUpdateComponent } from './attendance-data-upload-update.component';

@Injectable({ providedIn: 'root' })
export class AttendanceDataUploadResolve implements Resolve<IAttendanceDataUpload> {
  constructor() {}

  resolve(): Observable<IAttendanceDataUpload> | Observable<never> {
    return of(new AttendanceDataUpload());
  }
}

export const attendanceDataUploadRoute: Routes = [
  {
    path: 'new',
    component: AttendanceDataUploadUpdateComponent,
    resolve: {
      attendanceDataUpload: AttendanceDataUploadResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendanceDataUploads',
    },
    canActivate: [UserRouteAccessService],
  },
];
