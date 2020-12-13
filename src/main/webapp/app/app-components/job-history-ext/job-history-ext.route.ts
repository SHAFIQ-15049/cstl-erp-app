import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobHistory, JobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryExtService } from './job-history-ext.service';
import { JobHistoryExtComponent } from './job-history-ext.component';
import { JobHistoryExtDetailComponent } from './job-history-ext-detail.component';
import { JobHistoryExtUpdateComponent } from './job-history-ext-update.component';
import {EmployeeExtService} from "app/app-components/employee-ext/employee-ext.service";
import {Employee} from "app/shared/model/employee.model";
import {EducationalInfo} from "app/shared/model/educational-info.model";

@Injectable({ providedIn: 'root' })
export class JobHistoryExtResolve implements Resolve<IJobHistory> {
  constructor(private service: JobHistoryExtService, private router: Router, private employeeService: EmployeeExtService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobHistory> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobHistory: HttpResponse<JobHistory>) => {
          if (jobHistory.body) {
            return of(jobHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }   else if(employeeId){
      return this.employeeService.find(employeeId).pipe(
        flatMap((employee: HttpResponse<Employee>)=>{
          if(employee.body){
            const jobHistory = new JobHistory();
            jobHistory.employee = employee.body;
            return of(jobHistory);
          }else{
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobHistory());
  }
}

export const jobHistoryExtRoute: Routes = [
  {
    path: '',
    component: JobHistoryExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'JobHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobHistoryExtDetailComponent,
    resolve: {
      jobHistory: JobHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobHistoryExtUpdateComponent,
    resolve: {
      jobHistory: JobHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/new',
    component: JobHistoryExtUpdateComponent,
    resolve: {
      jobHistory: JobHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobHistories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobHistoryExtUpdateComponent,
    resolve: {
      jobHistory: JobHistoryExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobHistories',
    },
    canActivate: [UserRouteAccessService],
  },
];
