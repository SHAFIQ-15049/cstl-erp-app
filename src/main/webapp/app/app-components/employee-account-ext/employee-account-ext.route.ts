import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeAccount, EmployeeAccount } from 'app/shared/model/employee-account.model';
import { EmployeeAccountExtService } from './employee-account-ext.service';
import { EmployeeAccountExtComponent } from './employee-account-ext.component';
import { EmployeeAccountExtDetailComponent } from './employee-account-ext-detail.component';
import { EmployeeAccountExtUpdateComponent } from './employee-account-ext-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeAccountExtResolve implements Resolve<IEmployeeAccount> {
  constructor(private service: EmployeeAccountExtService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeeAccount: HttpResponse<EmployeeAccount>) => {
          if (employeeAccount.body) {
            return of(employeeAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeAccount());
  }
}

export const employeeAccountExtRoute: Routes = [
  {
    path: '',
    component: EmployeeAccountExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeAccountExtDetailComponent,
    resolve: {
      employeeAccount: EmployeeAccountExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeAccountExtUpdateComponent,
    resolve: {
      employeeAccount: EmployeeAccountExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeAccountExtUpdateComponent,
    resolve: {
      employeeAccount: EmployeeAccountExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
];
