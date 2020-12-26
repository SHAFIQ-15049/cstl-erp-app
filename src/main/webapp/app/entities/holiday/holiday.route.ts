import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHoliday, Holiday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { HolidayComponent } from './holiday.component';
import { HolidayDetailComponent } from './holiday-detail.component';
import { HolidayUpdateComponent } from './holiday-update.component';

@Injectable({ providedIn: 'root' })
export class HolidayResolve implements Resolve<IHoliday> {
  constructor(private service: HolidayService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHoliday> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((holiday: HttpResponse<Holiday>) => {
          if (holiday.body) {
            return of(holiday.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Holiday());
  }
}

export const holidayRoute: Routes = [
  {
    path: '',
    component: HolidayComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Holidays',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HolidayDetailComponent,
    resolve: {
      holiday: HolidayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Holidays',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HolidayUpdateComponent,
    resolve: {
      holiday: HolidayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Holidays',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HolidayUpdateComponent,
    resolve: {
      holiday: HolidayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Holidays',
    },
    canActivate: [UserRouteAccessService],
  },
];
