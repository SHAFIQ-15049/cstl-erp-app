import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFinePaymentHistory, FinePaymentHistory } from 'app/shared/model/fine-payment-history.model';
import { FinePaymentHistoryService } from './fine-payment-history.service';
import { FinePaymentHistoryComponent } from './fine-payment-history.component';
import { FinePaymentHistoryDetailComponent } from './fine-payment-history-detail.component';
import { FinePaymentHistoryUpdateComponent } from './fine-payment-history-update.component';
import { FineService } from 'app/entities/fine/fine.service';

@Injectable({ providedIn: 'root' })
export class FinePaymentHistoryResolve implements Resolve<IFinePaymentHistory> {
  constructor(private service: FinePaymentHistoryService, private fineService: FineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFinePaymentHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((finePaymentHistory: HttpResponse<FinePaymentHistory>) => {
          if (finePaymentHistory.body) {
            return of(finePaymentHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FinePaymentHistory());
  }
}

export const finePaymentHistoryRoute: Routes = [
  {
    path: '',
    component: FinePaymentHistoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'FinePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FinePaymentHistoryDetailComponent,
    resolve: {
      finePaymentHistory: FinePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FinePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FinePaymentHistoryUpdateComponent,
    resolve: {
      finePaymentHistory: FinePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FinePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FinePaymentHistoryUpdateComponent,
    resolve: {
      finePaymentHistory: FinePaymentHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FinePaymentHistories',
    },
    canActivate: [UserRouteAccessService],
  },
];
