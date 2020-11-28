import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDistrict, District } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';
import { DistrictComponent } from './district.component';
import { DistrictDetailComponent } from './district-detail.component';
import { DistrictUpdateComponent } from './district-update.component';

@Injectable({ providedIn: 'root' })
export class DistrictResolve implements Resolve<IDistrict> {
  constructor(private service: DistrictService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistrict> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((district: HttpResponse<District>) => {
          if (district.body) {
            return of(district.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new District());
  }
}

export const districtRoute: Routes = [
  {
    path: '',
    component: DistrictComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Districts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistrictDetailComponent,
    resolve: {
      district: DistrictResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Districts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistrictUpdateComponent,
    resolve: {
      district: DistrictResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Districts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistrictUpdateComponent,
    resolve: {
      district: DistrictResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Districts',
    },
    canActivate: [UserRouteAccessService],
  },
];
