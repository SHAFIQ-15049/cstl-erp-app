import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceHistory, ServiceHistory } from 'app/shared/model/service-history.model';
import { ServiceHistoryService } from './service-history.service';
import { ServiceHistoryComponent } from './service-history.component';
import { ServiceHistoryDetailComponent } from './service-history-detail.component';
import { ServiceHistoryUpdateComponent } from './service-history-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceHistoryResolve implements Resolve<IServiceHistory> {
  constructor(private service: ServiceHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceHistory: HttpResponse<ServiceHistory>) => {
          if (serviceHistory.body) {
            return of(serviceHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceHistory());
  }
}

export const serviceHistoryRoute: Routes = [
  {
    path: '',
    component: ServiceHistoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceHistoryDetailComponent,
    resolve: {
      serviceHistory: ServiceHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceHistoryUpdateComponent,
    resolve: {
      serviceHistory: ServiceHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceHistoryUpdateComponent,
    resolve: {
      serviceHistory: ServiceHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
];
