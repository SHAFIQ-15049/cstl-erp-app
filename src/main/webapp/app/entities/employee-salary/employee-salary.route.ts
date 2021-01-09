import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeSalary, EmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';
import { EmployeeSalaryComponent } from './employee-salary.component';
import { EmployeeSalaryDetailComponent } from './employee-salary-detail.component';
import { EmployeeSalaryUpdateComponent } from './employee-salary-update.component';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Injectable({ providedIn: 'root' })
export class EmployeeSalaryResolve implements Resolve<IEmployeeSalary> {
  constructor(private service: EmployeeSalaryService, private employeeService: EmployeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeSalary> | Observable<never> {
    const id = route.params['id'];
    const employeeID = route.params['employeeId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeeSalary: HttpResponse<EmployeeSalary>) => {
          if (employeeSalary.body) {
            return of(employeeSalary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (employeeID) {
      return this.employeeService.find(employeeID).pipe(
        flatMap(employee => {
          if (employee.body) {
            const employeeSalary = new EmployeeSalary();
            employeeSalary.employee = employee.body;
            return of(employeeSalary);
          } else {
            return of(new EmployeeSalary());
          }
        })
      );
    }
    return of(new EmployeeSalary());
  }
}

export const employeeSalaryRoute: Routes = [
  {
    path: '',
    component: EmployeeSalaryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'EmployeeSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeSalaryDetailComponent,
    resolve: {
      employeeSalary: EmployeeSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeSalaryUpdateComponent,
    resolve: {
      employeeSalary: EmployeeSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/:employeeId',
    component: EmployeeSalaryUpdateComponent,
    resolve: {
      employeeSalary: EmployeeSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeSalaryUpdateComponent,
    resolve: {
      employeeSalary: EmployeeSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EmployeeSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
];
