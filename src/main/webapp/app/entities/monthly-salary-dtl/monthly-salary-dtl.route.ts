import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMonthlySalaryDtl, MonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { MonthlySalaryDtlService } from './monthly-salary-dtl.service';
import { MonthlySalaryDtlComponent } from './monthly-salary-dtl.component';
import { MonthlySalaryDtlDetailComponent } from './monthly-salary-dtl-detail.component';
import { MonthlySalaryDtlUpdateComponent } from './monthly-salary-dtl-update.component';

@Injectable({ providedIn: 'root' })
export class MonthlySalaryDtlResolve implements Resolve<IMonthlySalaryDtl> {
  constructor(private service: MonthlySalaryDtlService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMonthlySalaryDtl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((monthlySalaryDtl: HttpResponse<MonthlySalaryDtl>) => {
          if (monthlySalaryDtl.body) {
            return of(monthlySalaryDtl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MonthlySalaryDtl());
  }
}

export const monthlySalaryDtlRoute: Routes = [
  {
    path: '',
    component: MonthlySalaryDtlComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'MonthlySalaryDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MonthlySalaryDtlDetailComponent,
    resolve: {
      monthlySalaryDtl: MonthlySalaryDtlResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MonthlySalaryDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MonthlySalaryDtlUpdateComponent,
    resolve: {
      monthlySalaryDtl: MonthlySalaryDtlResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MonthlySalaryDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MonthlySalaryDtlUpdateComponent,
    resolve: {
      monthlySalaryDtl: MonthlySalaryDtlResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MonthlySalaryDtls',
    },
    canActivate: [UserRouteAccessService],
  },
];
