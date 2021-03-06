import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { HolidayType, IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';
import { HolidayTypeComponent } from './holiday-type.component';
import { HolidayTypeDetailComponent } from './holiday-type-detail.component';
import { HolidayTypeUpdateComponent } from './holiday-type-update.component';

@Injectable({ providedIn: 'root' })
export class HolidayTypeResolve implements Resolve<IHolidayType> {
  constructor(private service: HolidayTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHolidayType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((holidayType: HttpResponse<HolidayType>) => {
          if (holidayType.body) {
            return of(holidayType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HolidayType());
  }
}

export const holidayTypeRoute: Routes = [
  {
    path: '',
    component: HolidayTypeComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.HOLIDAY_ADMIN, Authority.HOLIDAY_MANAGER],
      pageTitle: 'HolidayTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HolidayTypeDetailComponent,
    resolve: {
      holidayType: HolidayTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.HOLIDAY_ADMIN, Authority.HOLIDAY_MANAGER],
      pageTitle: 'HolidayTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HolidayTypeUpdateComponent,
    resolve: {
      holidayType: HolidayTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.HOLIDAY_ADMIN],
      pageTitle: 'HolidayTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HolidayTypeUpdateComponent,
    resolve: {
      holidayType: HolidayTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.HOLIDAY_ADMIN],
      pageTitle: 'HolidayTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
