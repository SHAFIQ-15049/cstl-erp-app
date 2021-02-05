import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { AttendanceSummary, IAttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { AttendanceSummaryService } from './attendance-summary.service';
import { AttendanceSummaryComponent } from './attendance-summary.component';

@Injectable({ providedIn: 'root' })
export class AttendanceSummaryResolve implements Resolve<IAttendanceSummary> {
  constructor(private service: AttendanceSummaryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendanceSummary> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendanceSummary: HttpResponse<AttendanceSummary>) => {
          if (attendanceSummary.body) {
            return of(attendanceSummary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttendanceSummary());
  }
}

export const attendanceSummaryRoute: Routes = [
  {
    path: '',
    component: AttendanceSummaryComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.ATTENDANCE_ADMIN, Authority.ATTENDANCE_MANAGER],
      pageTitle: 'AttendanceSummaries',
    },
    canActivate: [UserRouteAccessService],
  },
];
