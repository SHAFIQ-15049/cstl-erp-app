import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIdCardManagement, IdCardManagement } from 'app/shared/model/id-card-management.model';
import { IdCardManagementService } from './id-card-management.service';
import { IdCardManagementComponent } from './id-card-management.component';
import { IdCardManagementDetailComponent } from './id-card-management-detail.component';
import { IdCardManagementUpdateComponent } from './id-card-management-update.component';

@Injectable({ providedIn: 'root' })
export class IdCardManagementResolve implements Resolve<IIdCardManagement> {
  constructor(private service: IdCardManagementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdCardManagement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((idCardManagement: HttpResponse<IdCardManagement>) => {
          if (idCardManagement.body) {
            return of(idCardManagement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IdCardManagement());
  }
}

export const idCardManagementRoute: Routes = [
  {
    path: '',
    component: IdCardManagementComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'IdCardManagements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId',
    component: IdCardManagementComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,desc',
      pageTitle: 'IdCardManagements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IdCardManagementDetailComponent,
    resolve: {
      idCardManagement: IdCardManagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IdCardManagements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IdCardManagementUpdateComponent,
    resolve: {
      idCardManagement: IdCardManagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IdCardManagements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/:employeeId',
    component: IdCardManagementUpdateComponent,
    resolve: {
      idCardManagement: IdCardManagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IdCardManagements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IdCardManagementUpdateComponent,
    resolve: {
      idCardManagement: IdCardManagementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IdCardManagements',
    },
    canActivate: [UserRouteAccessService],
  },
];
