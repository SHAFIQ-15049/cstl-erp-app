import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOverTime, OverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from './over-time.service';
import { OverTimeComponent } from './over-time.component';
import { OverTimeDetailComponent } from './over-time-detail.component';
import { OverTimeUpdateComponent } from './over-time-update.component';

@Injectable({ providedIn: 'root' })
export class OverTimeResolve implements Resolve<IOverTime> {
  constructor(private service: OverTimeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOverTime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((overTime: HttpResponse<OverTime>) => {
          if (overTime.body) {
            return of(overTime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OverTime());
  }
}

export const overTimeRoute: Routes = [
  {
    path: '',
    component: OverTimeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'OverTimes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':selectedYear/:selectedMonth',
    component: OverTimeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'OverTimes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OverTimeDetailComponent,
    resolve: {
      overTime: OverTimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OverTimes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OverTimeUpdateComponent,
    resolve: {
      overTime: OverTimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OverTimes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OverTimeUpdateComponent,
    resolve: {
      overTime: OverTimeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OverTimes',
    },
    canActivate: [UserRouteAccessService],
  },
];
