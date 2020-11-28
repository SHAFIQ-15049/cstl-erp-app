import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyExtendedService } from 'app/app-components/company-extended/company-extended.service';
import { CompanyExtendedComponent } from 'app/app-components/company-extended/company-extended.component';
import { CompanyDetailComponent } from 'app/entities/company/company-detail.component';
import { CompanyExtendedUpdateComponent } from 'app/app-components/company-extended/company-extended-update.component';
import { CompanyExtendedDetailComponent } from 'app/app-components/company-extended/company-extended-detail.component';

@Injectable({ providedIn: 'root' })
export class CompanyExtendedResolve implements Resolve<ICompany> {
  constructor(private service: CompanyExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompany> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((company: HttpResponse<Company>) => {
          if (company.body) {
            return of(company.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Company());
  }
}

export const companyExtendedRoute: Routes = [
  {
    path: '',
    component: CompanyExtendedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Companies',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyExtendedDetailComponent,
    resolve: {
      company: CompanyExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Companies',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyExtendedUpdateComponent,
    resolve: {
      company: CompanyExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Companies',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyExtendedUpdateComponent,
    resolve: {
      company: CompanyExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Companies',
    },
    canActivate: [UserRouteAccessService],
  },
];
