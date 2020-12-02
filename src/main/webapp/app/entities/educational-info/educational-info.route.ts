import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEducationalInfo, EducationalInfo } from 'app/shared/model/educational-info.model';
import { EducationalInfoService } from './educational-info.service';
import { EducationalInfoComponent } from './educational-info.component';
import { EducationalInfoDetailComponent } from './educational-info-detail.component';
import { EducationalInfoUpdateComponent } from './educational-info-update.component';
import {EmployeeService} from "app/entities/employee/employee.service";
import {Employee} from "app/shared/model/employee.model";
import {Address} from "app/shared/model/address.model";

@Injectable({ providedIn: 'root' })
export class EducationalInfoResolve implements Resolve<IEducationalInfo> {
  constructor(private service: EducationalInfoService, private employeeService: EmployeeService, private router: Router) {}

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

export const educationalInfoRoute: Routes = [
  {
    path: '',
    component: EducationalInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationalInfoDetailComponent,
    resolve: {
      educationalInfo: EducationalInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationalInfoUpdateComponent,
    resolve: {
      educationalInfo: EducationalInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationalInfoUpdateComponent,
    resolve: {
      educationalInfo: EducationalInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EducationalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
];
