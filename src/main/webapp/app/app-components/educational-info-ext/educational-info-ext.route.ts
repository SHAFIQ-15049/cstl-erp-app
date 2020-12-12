import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEducationalInfo, EducationalInfo } from 'app/shared/model/educational-info.model';
import { EducationalInfoExtService } from './educational-info-ext.service';
import { EducationalInfoExtComponent } from './educational-info-ext.component';
import { EducationalInfoExtDetailComponent } from './educational-info-ext-detail.component';
import { EducationalInfoExtUpdateComponent } from './educational-info-ext-update.component';
import {EmployeeService} from "app/entities/employee/employee.service";
import {Employee} from "app/shared/model/employee.model";
import {Address} from "app/shared/model/address.model";

@Injectable({ providedIn: 'root' })
export class EducationalInfoExtResolve implements Resolve<IEducationalInfo> {
  constructor(private service: EducationalInfoExtService, private employeeService: EmployeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEducationalInfo> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((educationalInfo: HttpResponse<EducationalInfo>) => {
          if (educationalInfo.body) {
            return of(educationalInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    else if(employeeId){
      return this.employeeService.find(employeeId).pipe(
        flatMap((employee: HttpResponse<Employee>)=>{
          if(employee.body){
            const educationalInfo = new EducationalInfo();
            educationalInfo.employee = employee.body;
            return of(educationalInfo);
          }else{
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EducationalInfo());
  }
}

export const educationalInfoExtRoute: Routes = [
  {
    path: '',
    component: EducationalInfoExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/employee-view',
    component: EducationalInfoExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationalInfoExtDetailComponent,
    resolve: {
      educationalInfo: EducationalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationalInfoExtUpdateComponent,
    resolve: {
      educationalInfo: EducationalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/new',
    component: EducationalInfoExtUpdateComponent,
    resolve: {
      educationalInfo: EducationalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationalInfoExtUpdateComponent,
    resolve: {
      educationalInfo: EducationalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
];
