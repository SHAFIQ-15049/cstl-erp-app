import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWeekendDateMap, WeekendDateMap } from 'app/shared/model/weekend-date-map.model';
import { WeekendDateMapService } from './weekend-date-map.service';
import { WeekendDateMapComponent } from './weekend-date-map.component';

@Injectable({ providedIn: 'root' })
export class WeekendDateMapResolve implements Resolve<IWeekendDateMap> {
  constructor(private service: WeekendDateMapService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeekendDateMap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((weekendDateMap: HttpResponse<WeekendDateMap>) => {
          if (weekendDateMap.body) {
            return of(weekendDateMap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WeekendDateMap());
  }
}

export const weekendDateMapRoute: Routes = [
  {
    path: '',
    component: WeekendDateMapComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WeekendDateMaps',
    },
    canActivate: [UserRouteAccessService],
  },
];
