import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDivision, Division } from 'app/shared/model/division.model';
import { DivisionService } from './division.service';
import { DivisionComponent } from './division.component';
import { DivisionDetailComponent } from './division-detail.component';
import { DivisionUpdateComponent } from './division-update.component';

@Injectable({ providedIn: 'root' })
export class DivisionResolve implements Resolve<IDivision> {
  constructor(private service: DivisionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDivision> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((division: HttpResponse<Division>) => {
          if (division.body) {
            return of(division.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Division());
  }
}

export const divisionRoute: Routes = [
  {
    path: '',
    component: DivisionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Divisions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DivisionDetailComponent,
    resolve: {
      division: DivisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Divisions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DivisionUpdateComponent,
    resolve: {
      division: DivisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Divisions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DivisionUpdateComponent,
    resolve: {
      division: DivisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Divisions',
    },
    canActivate: [UserRouteAccessService],
  },
];
