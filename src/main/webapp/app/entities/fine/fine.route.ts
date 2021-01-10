import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFine, Fine } from 'app/shared/model/fine.model';
import { FineService } from './fine.service';
import { FineComponent } from './fine.component';
import { FineDetailComponent } from './fine-detail.component';
import { FineUpdateComponent } from './fine-update.component';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Injectable({ providedIn: 'root' })
export class FineResolve implements Resolve<IFine> {
  constructor(private service: FineService, private employeeService: EmployeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFine> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];

    if (id) {
      return this.service.find(id).pipe(
        flatMap((fine: HttpResponse<Fine>) => {
          if (fine.body) {
            this.service.storeFineId(fine.body.id!);
            return of(fine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (employeeId) {
      return this.employeeService.find(employeeId).pipe(
        flatMap(employe => {
          if (employe.body) {
            const fine = new Fine();
            fine.employee = employe.body;
            return of(fine);
          } else {
            return of(new Fine());
          }
        })
      );
    }
    return of(new Fine());
  }
}

export const fineRoute: Routes = [
  {
    path: '',
    component: FineComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Fines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FineDetailComponent,
    resolve: {
      fine: FineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Fines',
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        loadChildren: () => import('../fine-payment-history/fine-payment-history.module').then(m => m.CodeNodeErpFinePaymentHistoryModule),
      },
    ],
  },
  {
    path: 'new',
    component: FineUpdateComponent,
    resolve: {
      fine: FineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Fines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/:employeeId',
    component: FineUpdateComponent,
    resolve: {
      fine: FineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Fines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FineUpdateComponent,
    resolve: {
      fine: FineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Fines',
    },
    canActivate: [UserRouteAccessService],
  },
];
