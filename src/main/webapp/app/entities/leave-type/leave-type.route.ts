import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILeaveType, LeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from './leave-type.service';
import { LeaveTypeComponent } from './leave-type.component';
import { LeaveTypeDetailComponent } from './leave-type-detail.component';
import { LeaveTypeUpdateComponent } from './leave-type-update.component';

@Injectable({ providedIn: 'root' })
export class LeaveTypeResolve implements Resolve<ILeaveType> {
  constructor(private service: LeaveTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeaveType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((leaveType: HttpResponse<LeaveType>) => {
          if (leaveType.body) {
            return of(leaveType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LeaveType());
  }
}

export const leaveTypeRoute: Routes = [
  {
    path: '',
    component: LeaveTypeComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.LEAVE_ADMIN, Authority.LEAVE_MANAGER],
      pageTitle: 'LeaveTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LeaveTypeDetailComponent,
    resolve: {
      leaveType: LeaveTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.LEAVE_ADMIN, Authority.LEAVE_MANAGER],
      pageTitle: 'LeaveTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LeaveTypeUpdateComponent,
    resolve: {
      leaveType: LeaveTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.LEAVE_ADMIN],
      pageTitle: 'LeaveTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LeaveTypeUpdateComponent,
    resolve: {
      leaveType: LeaveTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.LEAVE_ADMIN],
      pageTitle: 'LeaveTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
