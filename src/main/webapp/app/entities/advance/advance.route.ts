import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdvance, Advance } from 'app/shared/model/advance.model';
import { AdvanceService } from './advance.service';
import { AdvanceComponent } from './advance.component';
import { AdvanceDetailComponent } from './advance-detail.component';
import { AdvanceUpdateComponent } from './advance-update.component';

@Injectable({ providedIn: 'root' })
export class AdvanceResolve implements Resolve<IAdvance> {
  constructor(private service: AdvanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((advance: HttpResponse<Advance>) => {
          if (advance.body) {
            return of(advance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Advance());
  }
}

export const advanceRoute: Routes = [
  {
    path: '',
    component: AdvanceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Advances',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdvanceDetailComponent,
    resolve: {
      advance: AdvanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Advances',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdvanceUpdateComponent,
    resolve: {
      advance: AdvanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Advances',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdvanceUpdateComponent,
    resolve: {
      advance: AdvanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Advances',
    },
    canActivate: [UserRouteAccessService],
  },
];
