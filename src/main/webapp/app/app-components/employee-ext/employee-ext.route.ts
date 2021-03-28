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
import { IdCardComponent } from 'app/app-components/employee-ext/id-card/id-card.component';

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
      defaultSort: 'id,desc',
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
        path: 'personal-info',
        loadChildren: () => import('../personal-info-ext/personal-info-ext.module').then(m => m.CodeNodeErpPersonalInfoModule),
      },
      {
        path: 'address',
        loadChildren: () => import('../address-ext/address-ext.module').then(m => m.CodeNodeErpAddressModule),
      },
      {
        path: 'educational-info',
        loadChildren: () => import('../educational-info-ext/educational-info-ext.module').then(m => m.CodeNodeErpEducationalInfoModule),
      },
      {
        path: 'training',
        loadChildren: () => import('../training-ext/training-ext.module').then(m => m.CodeNodeErpTrainingModule),
      },
      {
        path: 'employee-account',
        loadChildren: () => import('../employee-account-ext/employee-account-ext.module').then(m => m.CodeNodeErpEmployeeAccountModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('../job-history-ext/job-history-ext.module').then(m => m.CodeNodeErpJobHistoryModule),
      },
      {
        path: 'service-history',
        loadChildren: () => import('../service-history-ext/service-history-ext.module').then(m => m.CodeNodeErpServiceHistoryModule),
      },
      {
        path: 'id-card',
        component: IdCardComponent,
        data: {
          authorities: [Authority.USER],
          pageTitle: 'ID Card',
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'id-card-management',
        loadChildren: () =>
          import('../../entities/id-card-management/id-card-management.module').then(m => m.CodeNodeErpIdCardManagementModule),
      },
      {
        path: 'employee-salary',
        loadChildren: () => import('../../entities/employee-salary/employee-salary.module').then(m => m.CodeNodeErpEmployeeSalaryModule),
      },
      {
        path: 'advance',
        loadChildren: () => import('../../entities/advance/advance.module').then(m => m.CodeNodeErpAdvanceModule),
      },
      {
        path: 'fine',
        loadChildren: () => import('../../entities/fine/fine.module').then(m => m.CodeNodeErpFineModule),
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
