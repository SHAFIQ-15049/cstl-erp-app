import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWeekend, Weekend } from 'app/shared/model/weekend.model';
import { WeekendService } from './weekend.service';
import { WeekendComponent } from './weekend.component';
import { WeekendDetailComponent } from './weekend-detail.component';
import { WeekendUpdateComponent } from './weekend-update.component';

@Injectable({ providedIn: 'root' })
export class WeekendResolve implements Resolve<IWeekend> {
  constructor(private service: WeekendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeekend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((weekend: HttpResponse<Weekend>) => {
          if (weekend.body) {
            return of(weekend.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Weekend());
  }
}

export const weekendRoute: Routes = [
  {
    path: '',
    component: WeekendComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.WEEKEND_ADMIN, Authority.WEEKEND_MANAGER],
      pageTitle: 'Weekends',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WeekendDetailComponent,
    resolve: {
      weekend: WeekendResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.WEEKEND_ADMIN, Authority.WEEKEND_MANAGER],
      pageTitle: 'Weekends',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WeekendUpdateComponent,
    resolve: {
      weekend: WeekendResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.WEEKEND_ADMIN],
      pageTitle: 'Weekends',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WeekendUpdateComponent,
    resolve: {
      weekend: WeekendResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.WEEKEND_ADMIN],
      pageTitle: 'Weekends',
    },
    canActivate: [UserRouteAccessService],
  },
];
