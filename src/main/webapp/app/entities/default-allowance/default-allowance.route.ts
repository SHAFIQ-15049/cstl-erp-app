import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDefaultAllowance, DefaultAllowance } from 'app/shared/model/default-allowance.model';
import { DefaultAllowanceService } from './default-allowance.service';
import { DefaultAllowanceComponent } from './default-allowance.component';
import { DefaultAllowanceDetailComponent } from './default-allowance-detail.component';
import { DefaultAllowanceUpdateComponent } from './default-allowance-update.component';

@Injectable({ providedIn: 'root' })
export class DefaultAllowanceResolve implements Resolve<IDefaultAllowance> {
  constructor(private service: DefaultAllowanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDefaultAllowance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((defaultAllowance: HttpResponse<DefaultAllowance>) => {
          if (defaultAllowance.body) {
            return of(defaultAllowance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DefaultAllowance());
  }
}

export const defaultAllowanceRoute: Routes = [
  {
    path: '',
    component: DefaultAllowanceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,desc',
      pageTitle: 'DefaultAllowances',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DefaultAllowanceDetailComponent,
    resolve: {
      defaultAllowance: DefaultAllowanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DefaultAllowances',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DefaultAllowanceUpdateComponent,
    resolve: {
      defaultAllowance: DefaultAllowanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DefaultAllowances',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DefaultAllowanceUpdateComponent,
    resolve: {
      defaultAllowance: DefaultAllowanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DefaultAllowances',
    },
    canActivate: [UserRouteAccessService],
  },
];
