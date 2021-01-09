import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFestivalAllowanceTimeLine, FestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';
import { FestivalAllowanceTimeLineService } from './festival-allowance-time-line.service';
import { FestivalAllowanceTimeLineComponent } from './festival-allowance-time-line.component';
import { FestivalAllowanceTimeLineDetailComponent } from './festival-allowance-time-line-detail.component';
import { FestivalAllowanceTimeLineUpdateComponent } from './festival-allowance-time-line-update.component';

@Injectable({ providedIn: 'root' })
export class FestivalAllowanceTimeLineResolve implements Resolve<IFestivalAllowanceTimeLine> {
  constructor(private service: FestivalAllowanceTimeLineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFestivalAllowanceTimeLine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((festivalAllowanceTimeLine: HttpResponse<FestivalAllowanceTimeLine>) => {
          if (festivalAllowanceTimeLine.body) {
            return of(festivalAllowanceTimeLine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FestivalAllowanceTimeLine());
  }
}

export const festivalAllowanceTimeLineRoute: Routes = [
  {
    path: '',
    component: FestivalAllowanceTimeLineComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'FestivalAllowanceTimeLines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FestivalAllowanceTimeLineDetailComponent,
    resolve: {
      festivalAllowanceTimeLine: FestivalAllowanceTimeLineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowanceTimeLines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FestivalAllowanceTimeLineUpdateComponent,
    resolve: {
      festivalAllowanceTimeLine: FestivalAllowanceTimeLineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowanceTimeLines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FestivalAllowanceTimeLineUpdateComponent,
    resolve: {
      festivalAllowanceTimeLine: FestivalAllowanceTimeLineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowanceTimeLines',
    },
    canActivate: [UserRouteAccessService],
  },
];
