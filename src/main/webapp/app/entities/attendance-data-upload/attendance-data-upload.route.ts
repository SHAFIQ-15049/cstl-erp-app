import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { AttendanceDataUpload, IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { AttendanceDataUploadService } from './attendance-data-upload.service';
import { AttendanceDataUploadUpdateComponent } from './attendance-data-upload-update.component';

@Injectable({ providedIn: 'root' })
export class AttendanceDataUploadResolve implements Resolve<IAttendanceDataUpload> {
  constructor(private service: AttendanceDataUploadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendanceDataUpload> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendanceDataUpload: HttpResponse<AttendanceDataUpload>) => {
          if (attendanceDataUpload.body) {
            return of(attendanceDataUpload.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
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
