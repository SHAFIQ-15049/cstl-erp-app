import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { AddressComponent } from './address.component';
import { AddressDetailComponent } from './address-detail.component';
import { AddressUpdateComponent } from './address-update.component';

@Injectable({ providedIn: 'root' })
export class AddressResolve implements Resolve<IAddress> {
  constructor(private service: AddressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((address: HttpResponse<Address>) => {
          if (address.body) {
            return of(address.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Address());
  }
}

export const addressRoute: Routes = [
  {
    path: '',
    component: AddressComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddressDetailComponent,
    resolve: {
      address: AddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddressUpdateComponent,
    resolve: {
      address: AddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddressUpdateComponent,
    resolve: {
      address: AddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
];
