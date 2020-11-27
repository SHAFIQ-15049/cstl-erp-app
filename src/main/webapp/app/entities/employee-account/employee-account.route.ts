import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeAccount, EmployeeAccount } from 'app/shared/model/employee-account.model';
import { EmployeeAccountService } from './employee-account.service';
import { EmployeeAccountComponent } from './employee-account.component';
import { EmployeeAccountDetailComponent } from './employee-account-detail.component';
import { EmployeeAccountUpdateComponent } from './employee-account-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeAccountResolve implements Resolve<IEmployeeAccount> {
  constructor(private service: EmployeeAccountService, private router: Router) {}

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

export const employeeAccountRoute: Routes = [
  {
    path: '',
    component: EmployeeAccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeAccountDetailComponent,
    resolve: {
      employeeAccount: EmployeeAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeAccountUpdateComponent,
    resolve: {
      employeeAccount: EmployeeAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeAccountUpdateComponent,
    resolve: {
      employeeAccount: EmployeeAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeAccounts',
    },
    canActivate: [UserRouteAccessService],
  },
];
