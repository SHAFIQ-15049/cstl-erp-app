import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingExtService } from './training-ext.service';
import { TrainingExtComponent } from './training-ext.component';
import { TrainingExtDetailComponent } from './training-ext-detail.component';
import { TrainingExtUpdateComponent } from './training-ext-update.component';
import {EmployeeExtService} from "app/app-components/employee-ext/employee-ext.service";
import {Employee} from "app/shared/model/employee.model";
import {JobHistory} from "app/shared/model/job-history.model";

@Injectable({ providedIn: 'root' })
export class TrainingExtResolve implements Resolve<ITraining> {
  constructor(private service: TrainingExtService, private router: Router, private employeeService: EmployeeExtService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITraining> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((training: HttpResponse<Training>) => {
          if (training.body) {
            return of(training.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }else if(employeeId){
      return this.employeeService.find(employeeId).pipe(
        flatMap((employee: HttpResponse<Employee>)=>{
          if(employee.body){
            const training = new Training();
            training.employee = employee.body;
            return of(training);
          }else{
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Training());
  }
}

export const trainingExtRoute: Routes = [
  {
    path: '',
    component: TrainingExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainingExtDetailComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingExtUpdateComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/new',
    component: TrainingExtUpdateComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingExtUpdateComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
];
