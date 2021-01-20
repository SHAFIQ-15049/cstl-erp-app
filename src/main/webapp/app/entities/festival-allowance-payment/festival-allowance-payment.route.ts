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
import { DesignationService } from 'app/entities/designation/designation.service';
import { Designation } from 'app/shared/model/designation.model';

@Injectable({ providedIn: 'root' })
export class FestivalAllowancePaymentResolve implements Resolve<IFestivalAllowancePayment> {
  constructor(private service: FestivalAllowancePaymentService, private router: Router, private designationService: DesignationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFestivalAllowancePayment> | Observable<never> {
    const id = route.params['id'];
    const year = route.params['year'];
    const month = route.params['month'];
    const designationId = route.params['designationId'];
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
    } else if (year && month && designationId) {
      return this.designationService.find(designationId).pipe(
        flatMap(res => {
          const festivalAllowancePayment = new FestivalAllowancePayment();
          festivalAllowancePayment.month = month;
          festivalAllowancePayment.year = year;
          festivalAllowancePayment.designation = res.body!;
          return of(festivalAllowancePayment);
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
    children: [
      {
        path: '',
        loadChildren: () =>
          import('../festival-allowance-payment-dtl/festival-allowance-payment-dtl.module').then(
            m => m.CodeNodeErpFestivalAllowancePaymentDtlModule
          ),
      },
    ],
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
    path: 'new/:year/:month/:designationId',
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
