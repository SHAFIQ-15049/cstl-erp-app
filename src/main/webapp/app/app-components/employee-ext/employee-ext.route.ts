import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeExtService } from './employee-ext.service';
import { EmployeeExtComponent } from './employee-ext.component';
import { EmployeeExtDetailComponent } from './employee-ext-detail.component';
import { EmployeeExtUpdateComponent } from './employee-ext-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeExtResolve implements Resolve<IEmployee> {
  constructor(private service: EmployeeExtService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employee: HttpResponse<Employee>) => {
          if (employee.body) {
            return of(employee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Employee());
  }
}

export const employeeExtRoute: Routes = [
  {
    path: '',
    component: EmployeeExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeExtDetailComponent,
    resolve: {
      employee: EmployeeExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: 'personal-info-employee',
        loadChildren: () => import('../personal-info-ext/personal-info-ext.module').then(m => m.CodeNodeErpPersonalInfoModule),
        outlet: 'emp'
      },
      {
        path: 'address-employee',
        loadChildren: () => import('../address-ext/address-ext.module').then(m => m.CodeNodeErpAddressModule),
        outlet: 'emp'
      },
      {
        path: 'educational-info',
        loadChildren: () => import('../educational-info-ext/educational-info-ext.module').then(m => m.CodeNodeErpEducationalInfoModule),
        outlet: 'emp'
      },
      {
        path: 'training',
        loadChildren: () => import('../training-ext/training-ext.module').then(m => m.CodeNodeErpTrainingModule),
        outlet: 'emp'
      },
      {
        path: 'employee-account',
        loadChildren: () => import('../employee-account-ext/employee-account-ext.module').then(m => m.CodeNodeErpEmployeeAccountModule),
        outlet: 'emp'
      },
      {
        path: 'job-history',
        loadChildren: () => import('../job-history-ext/job-history-ext.module').then(m => m.CodeNodeErpJobHistoryModule),
        outlet: 'emp'
      },
      {
        path: 'service-history',
        loadChildren: () => import('../service-history-ext/service-history-ext.module').then(m => m.CodeNodeErpServiceHistoryModule),
        outlet: 'emp'
      },
    ],
  },
  {
    path: 'new',
    component: EmployeeExtUpdateComponent,
    resolve: {
      employee: EmployeeExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeExtUpdateComponent,
    resolve: {
      employee: EmployeeExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
  },
];
