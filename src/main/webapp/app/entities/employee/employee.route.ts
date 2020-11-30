import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { EmployeeComponent } from './employee.component';
import { EmployeeDetailComponent } from './employee-detail.component';
import { EmployeeUpdateComponent } from './employee-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeResolve implements Resolve<IEmployee> {
  constructor(private service: EmployeeService, private router: Router) {}

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

export const employeeRoute: Routes = [
  {
    path: '',
    component: EmployeeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeDetailComponent,
    resolve: {
      employee: EmployeeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: 'personal-info-employee',
        loadChildren: () => import('../personal-info/personal-info.module').then(m => m.CodeNodeErpPersonalInfoModule),
      },
      {
        path: 'address-employee',
        loadChildren: () => import('../address/address.module').then(m => m.CodeNodeErpAddressModule),
      },
      {
        path: 'educational-info-employee',
        loadChildren: () => import('../educational-info/educational-info.module').then(m => m.CodeNodeErpEducationalInfoModule),
      },
      {
        path: 'training-employee',
        loadChildren: () => import('../training/training.module').then(m => m.CodeNodeErpTrainingModule),
      },
      {
        path: 'employee-account',
        loadChildren: () => import('../employee-account/employee-account.module').then(m => m.CodeNodeErpEmployeeAccountModule),
      },
      {
        path: 'job-history-employee',
        loadChildren: () => import('../job-history/job-history.module').then(m => m.CodeNodeErpJobHistoryModule),
      },
      {
        path: 'service-history-employee',
        loadChildren: () => import('../service-history/service-history.module').then(m => m.CodeNodeErpServiceHistoryModule),
      },
    ],
  },
  {
    path: 'new',
    component: EmployeeUpdateComponent,
    resolve: {
      employee: EmployeeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeUpdateComponent,
    resolve: {
      employee: EmployeeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Employees',
    },
    canActivate: [UserRouteAccessService],
  },
];
