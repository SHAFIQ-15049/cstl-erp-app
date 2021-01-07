import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFestivalAllowancePaymentDtl, FestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';
import { FestivalAllowancePaymentDtlService } from './festival-allowance-payment-dtl.service';
import { FestivalAllowancePaymentDtlComponent } from './festival-allowance-payment-dtl.component';
import { FestivalAllowancePaymentDtlDetailComponent } from './festival-allowance-payment-dtl-detail.component';
import { FestivalAllowancePaymentDtlUpdateComponent } from './festival-allowance-payment-dtl-update.component';

@Injectable({ providedIn: 'root' })
export class FestivalAllowancePaymentDtlResolve implements Resolve<IFestivalAllowancePaymentDtl> {
  constructor(private service: FestivalAllowancePaymentDtlService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFestivalAllowancePaymentDtl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((festivalAllowancePaymentDtl: HttpResponse<FestivalAllowancePaymentDtl>) => {
          if (festivalAllowancePaymentDtl.body) {
            return of(festivalAllowancePaymentDtl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FestivalAllowancePaymentDtl());
  }
}

export const festivalAllowancePaymentDtlRoute: Routes = [
  {
    path: '',
    component: FestivalAllowancePaymentDtlComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'FestivalAllowancePaymentDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FestivalAllowancePaymentDtlDetailComponent,
    resolve: {
      festivalAllowancePaymentDtl: FestivalAllowancePaymentDtlResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowancePaymentDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FestivalAllowancePaymentDtlUpdateComponent,
    resolve: {
      festivalAllowancePaymentDtl: FestivalAllowancePaymentDtlResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowancePaymentDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FestivalAllowancePaymentDtlUpdateComponent,
    resolve: {
      festivalAllowancePaymentDtl: FestivalAllowancePaymentDtlResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowancePaymentDtls',
    },
    canActivate: [UserRouteAccessService],
  },
];
