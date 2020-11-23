import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDesignation, Designation } from 'app/shared/model/designation.model';
import { DesignationService } from './designation.service';
import { DesignationComponent } from './designation.component';
import { DesignationDetailComponent } from './designation-detail.component';
import { DesignationUpdateComponent } from './designation-update.component';

@Injectable({ providedIn: 'root' })
export class DesignationResolve implements Resolve<IDesignation> {
  constructor(private service: DesignationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDesignation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((designation: HttpResponse<Designation>) => {
          if (designation.body) {
            return of(designation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Designation());
  }
}

export const designationRoute: Routes = [
  {
    path: '',
    component: DesignationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Designations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DesignationDetailComponent,
    resolve: {
      designation: DesignationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Designations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DesignationUpdateComponent,
    resolve: {
      designation: DesignationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Designations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DesignationUpdateComponent,
    resolve: {
      designation: DesignationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Designations',
    },
    canActivate: [UserRouteAccessService],
  },
];
