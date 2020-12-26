import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { LeaveApplicationComponent } from './leave-application.component';
import { LeaveApplicationDetailComponent } from './leave-application-detail.component';
import { LeaveApplicationUpdateComponent } from './leave-application-update.component';
import { LeaveApplicationReviewComponent } from 'app/entities/leave-application/leave-application-review.component';
import { LeaveApplicationActionUpdateComponent } from 'app/entities/leave-application/leave-application-action-update.component';

@Injectable({ providedIn: 'root' })
export class LeaveApplicationResolve implements Resolve<ILeaveApplication> {
  constructor(private service: LeaveApplicationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeaveApplication> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((leaveApplication: HttpResponse<LeaveApplication>) => {
          if (leaveApplication.body) {
            return of(leaveApplication.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LeaveApplication());
  }
}

let LeaveApplicationActionComponent;
export const leaveApplicationRoute: Routes = [
  {
    path: '',
    component: LeaveApplicationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LeaveApplicationDetailComponent,
    resolve: {
      leaveApplication: LeaveApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LeaveApplicationUpdateComponent,
    resolve: {
      leaveApplication: LeaveApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LeaveApplicationUpdateComponent,
    resolve: {
      leaveApplication: LeaveApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'review',
    component: LeaveApplicationReviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/action',
    component: LeaveApplicationActionUpdateComponent,
    resolve: {
      leaveApplication: LeaveApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
];
