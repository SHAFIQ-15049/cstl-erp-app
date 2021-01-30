import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { LeaveApplicationComponent } from './leave-application.component';
import { LeaveApplicationDetailComponent } from './leave-application-detail.component';
import { LeaveApplicationUpdateComponent } from './leave-application-update.component';
import { LeaveApplicationReviewFirstAuthorityComponent } from 'app/entities/leave-application/leave-application-review-first-authority.component';
import { OtherLeaveApplicationUpdateComponent } from 'app/entities/leave-application/other-leave-application-update.component';
import { OtherLeaveApplicationComponent } from 'app/entities/leave-application/other-leave-application.component';
import { LeaveApplicationReviewSecondAuthorityComponent } from 'app/entities/leave-application/leave-application-review-second-authority.component';
import { LeaveApplicationActionByFirstAuthorityUpdateComponent } from 'app/entities/leave-application/leave-application-action-by-first-authority-update.component';
import { LeaveApplicationActionBySecondAuthorityUpdateComponent } from 'app/entities/leave-application/leave-application-action-by-second-authority-update.component';

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
    path: 'review-first-authority',
    component: LeaveApplicationReviewFirstAuthorityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'review-second-authority',
    component: LeaveApplicationReviewSecondAuthorityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LeaveApplications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/action-by-first-authority',
    component: LeaveApplicationActionByFirstAuthorityUpdateComponent,
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
    path: ':id/action-by-second-authority',
    component: LeaveApplicationActionBySecondAuthorityUpdateComponent,
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
    path: 'others-leave',
    component: OtherLeaveApplicationComponent,
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
    path: 'others-leave/new',
    component: OtherLeaveApplicationUpdateComponent,
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
