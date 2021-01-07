import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMonthlySalary, MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from './monthly-salary.service';
import { MonthlySalaryComponent } from './monthly-salary.component';
import { MonthlySalaryDetailComponent } from './monthly-salary-detail.component';
import { MonthlySalaryUpdateComponent } from './monthly-salary-update.component';

@Injectable({ providedIn: 'root' })
export class MonthlySalaryResolve implements Resolve<IMonthlySalary> {
  constructor(private service: MonthlySalaryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMonthlySalary> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((monthlySalary: HttpResponse<MonthlySalary>) => {
          if (monthlySalary.body) {
            return of(monthlySalary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MonthlySalary());
  }
}

export const monthlySalaryRoute: Routes = [
  {
    path: '',
    component: MonthlySalaryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'MonthlySalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MonthlySalaryDetailComponent,
    resolve: {
      monthlySalary: MonthlySalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MonthlySalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MonthlySalaryUpdateComponent,
    resolve: {
      monthlySalary: MonthlySalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MonthlySalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MonthlySalaryUpdateComponent,
    resolve: {
      monthlySalary: MonthlySalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MonthlySalaries',
    },
    canActivate: [UserRouteAccessService],
  },
];
