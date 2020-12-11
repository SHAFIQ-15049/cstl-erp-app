import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import {Observable, of, EMPTY, forkJoin} from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressExtService } from './address-ext.service';
import { AddressExtComponent } from './address-ext.component';
import { AddressExtDetailComponent } from './address-ext-detail.component';
import { AddressExtUpdateComponent } from './address-ext-update.component';
import {EmployeeService} from "app/entities/employee/employee.service";
import {Employee} from "app/shared/model/employee.model";

@Injectable({ providedIn: 'root' })
export class AddressExtResolve implements Resolve<IAddress> {
  constructor(private service: AddressExtService, private employeeService: EmployeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddress> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];
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
    else if(employeeId){
     return   forkJoin(
        this.employeeService.find(employeeId),
        this.service.query({'employeeId.equals': employeeId})
      ).pipe(
        flatMap((res)=>{
          const employee = res[0].body;
          const address = res[1].body && res[1].body?.length>0? res[1].body[0]: new Address();
          if(address?.id){
            return of(address);
          }else{
            address.employee = employee!;
            return of(address);
          }
        })
      );
    }
    return of(new Address());
  }
}

export const addressExtRoute: Routes = [
  {
    path: '',
    component: AddressExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddressExtDetailComponent,
    resolve: {
      address: AddressExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/employee-view',
    component: AddressExtDetailComponent,
    resolve: {
      address: AddressExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddressExtUpdateComponent,
    resolve: {
      address: AddressExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/new',
    component: AddressExtUpdateComponent,
    resolve: {
      address: AddressExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddressExtUpdateComponent,
    resolve: {
      address: AddressExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Addresses',
    },
    canActivate: [UserRouteAccessService],
  },
];
