import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceHistory, ServiceHistory } from 'app/shared/model/service-history.model';
import { ServiceHistoryExtService } from './service-history-ext.service';
import { ServiceHistoryExtComponent } from './service-history-ext.component';
import { ServiceHistoryExtDetailComponent } from './service-history-ext-detail.component';
import { ServiceHistoryExtUpdateComponent } from './service-history-ext-update.component';
import {EmployeeExtService} from "app/app-components/employee-ext/employee-ext.service";

@Injectable({ providedIn: 'root' })
export class ServiceHistoryExtResolve implements Resolve<IServiceHistory> {
  constructor(private service: ServiceHistoryExtService, private router: Router, private employeeService: EmployeeExtService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceHistory> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceHistory: HttpResponse<ServiceHistory>) => {
          if (serviceHistory.body) {
            return of(serviceHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }else if(employeeId){
      return this.employeeService.find(employeeId).pipe(
        flatMap((employee)=>{
          if(employee.body){
            const serviceHistory = new ServiceHistory();
            serviceHistory.employee = employee.body;
            return of(serviceHistory);
          }else{
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceHistory());
  }
}

export const serviceHistoryExtRoute: Routes = [
  {
    path: '',
    component: ServiceHistoryExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceHistoryExtDetailComponent,
    resolve: {
      serviceHistory: ServiceHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceHistoryExtUpdateComponent,
    resolve: {
      serviceHistory: ServiceHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/new',
    component: ServiceHistoryExtUpdateComponent,
    resolve: {
      serviceHistory: ServiceHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceHistoryExtUpdateComponent,
    resolve: {
      serviceHistory: ServiceHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ServiceHistories',
    },
    canActivate: [UserRouteAccessService],
  },
];
