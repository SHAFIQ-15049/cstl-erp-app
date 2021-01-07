import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdvancePaymentHistory, AdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';
import { AdvancePaymentHistoryService } from './advance-payment-history.service';
import { AdvancePaymentHistoryComponent } from './advance-payment-history.component';
import { AdvancePaymentHistoryDetailComponent } from './advance-payment-history-detail.component';
import { AdvancePaymentHistoryUpdateComponent } from './advance-payment-history-update.component';

@Injectable({ providedIn: 'root' })
export class AdvancePaymentHistoryResolve implements Resolve<IAdvancePaymentHistory> {
  constructor(private service: AdvancePaymentHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvancePaymentHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((advancePaymentHistory: HttpResponse<AdvancePaymentHistory>) => {
          if (advancePaymentHistory.body) {
            return of(advancePaymentHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdvancePaymentHistory());
  }
}

export const advancePaymentHistoryRoute: Routes = [
  {
    path: '',
    component: AdvancePaymentHistoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AdvancePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdvancePaymentHistoryDetailComponent,
    resolve: {
      advancePaymentHistory: AdvancePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdvancePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdvancePaymentHistoryUpdateComponent,
    resolve: {
      advancePaymentHistory: AdvancePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdvancePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdvancePaymentHistoryUpdateComponent,
    resolve: {
      advancePaymentHistory: AdvancePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdvancePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
];
