import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFestivalAllowancePayment, FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { FestivalAllowancePaymentService } from './festival-allowance-payment.service';
import { FestivalAllowancePaymentComponent } from './festival-allowance-payment.component';
import { FestivalAllowancePaymentDetailComponent } from './festival-allowance-payment-detail.component';
import { FestivalAllowancePaymentUpdateComponent } from './festival-allowance-payment-update.component';

@Injectable({ providedIn: 'root' })
export class FestivalAllowancePaymentResolve implements Resolve<IFestivalAllowancePayment> {
  constructor(private service: FestivalAllowancePaymentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFestivalAllowancePayment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((festivalAllowancePayment: HttpResponse<FestivalAllowancePayment>) => {
          if (festivalAllowancePayment.body) {
            return of(festivalAllowancePayment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FestivalAllowancePayment());
  }
}

export const festivalAllowancePaymentRoute: Routes = [
  {
    path: '',
    component: FestivalAllowancePaymentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'FestivalAllowancePayments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':year/:month/:designationId',
    component: FestivalAllowancePaymentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'FestivalAllowancePayments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FestivalAllowancePaymentDetailComponent,
    resolve: {
      festivalAllowancePayment: FestivalAllowancePaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowancePayments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FestivalAllowancePaymentUpdateComponent,
    resolve: {
      festivalAllowancePayment: FestivalAllowancePaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowancePayments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FestivalAllowancePaymentUpdateComponent,
    resolve: {
      festivalAllowancePayment: FestivalAllowancePaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FestivalAllowancePayments',
    },
    canActivate: [UserRouteAccessService],
  },
];
