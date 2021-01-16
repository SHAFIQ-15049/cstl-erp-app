import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartialSalary, PartialSalary } from 'app/shared/model/partial-salary.model';
import { PartialSalaryService } from './partial-salary.service';
import { PartialSalaryComponent } from './partial-salary.component';
import { PartialSalaryDetailComponent } from './partial-salary-detail.component';
import { PartialSalaryUpdateComponent } from './partial-salary-update.component';

@Injectable({ providedIn: 'root' })
export class PartialSalaryResolve implements Resolve<IPartialSalary> {
  constructor(private service: PartialSalaryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartialSalary> | Observable<never> {
    const id = route.params['id'];
    const year = route.params['year'];
    const month = route.params['month'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partialSalary: HttpResponse<PartialSalary>) => {
          if (partialSalary.body) {
            return of(partialSalary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (year && month) {
      const partialSalary = new PartialSalary();
      partialSalary.year = year;
      partialSalary.month = month;
      return of(partialSalary);
    }
    return of(new PartialSalary());
  }
}

export const partialSalaryRoute: Routes = [
  {
    path: '',
    component: PartialSalaryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PartialSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartialSalaryDetailComponent,
    resolve: {
      partialSalary: PartialSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PartialSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartialSalaryUpdateComponent,
    resolve: {
      partialSalary: PartialSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PartialSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/:year/:month',
    component: PartialSalaryUpdateComponent,
    resolve: {
      partialSalary: PartialSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PartialSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartialSalaryUpdateComponent,
    resolve: {
      partialSalary: PartialSalaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PartialSalaries',
    },
    canActivate: [UserRouteAccessService],
  },
];
