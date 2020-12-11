import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import {Observable, of, EMPTY, forkJoin} from 'rxjs';
import {flatMap, map} from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonalInfo, PersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoExtService } from './personal-info-ext.service';
import { PersonalInfoExtComponent } from './personal-info-ext.component';
import { PersonalInfoExtDetailComponent } from './personal-info-ext-detail.component';
import { PersonalInfoExtUpdateComponent } from './personal-info-ext-update.component';
import {EmployeeService} from "app/entities/employee/employee.service";
import {Employee} from "app/shared/model/employee.model";
import {Address} from "app/shared/model/address.model";

@Injectable({ providedIn: 'root' })
export class PersonalInfoExtResolve implements Resolve<IPersonalInfo> {
  constructor(private service: PersonalInfoExtService, private employeeService: EmployeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonalInfo> | Observable<never> {
    const id = route.params['id'];
    const employeeId = route.params['employeeId'];

    if (id) {
      return this.service.find(id).pipe(
        flatMap((personalInfo: HttpResponse<PersonalInfo>) => {
          if (personalInfo.body) {
            return of(personalInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }else if(employeeId){
      return forkJoin(
        this.employeeService.find(employeeId),
        this.service.query({'employeeId.equals': employeeId})
      ).pipe(
        flatMap((res)=>{
          const employee = res[0].body;
          const personalInfoInt = res[1].body && res[1].body?.length>0?res[1].body[0]: new PersonalInfo();
          if(personalInfoInt.id){
            return of(personalInfoInt)
          }else{
            personalInfoInt.employee = employee!;
            return of(personalInfoInt);
          }
        })
      );
/*      return this.employeeService.find(employeeId).pipe(
        flatMap((employee: HttpResponse<Employee>)=>{
          if(employee.body){
            const personalInfo = new PersonalInfo();
            personalInfo.employee = employee.body;
            return of(personalInfo);
          }else{
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );*/
    }
    return of(new PersonalInfo());
  }
}

export const personalInfoExtRoute: Routes = [
  {
    path: '',
    component: PersonalInfoExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonalInfoExtDetailComponent,
    resolve: {
      personalInfo: PersonalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/employee-view',
    component: PersonalInfoExtDetailComponent,
    resolve: {
      personalInfo: PersonalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonalInfoExtUpdateComponent,
    resolve: {
      personalInfo: PersonalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':employeeId/new',
    component: PersonalInfoExtUpdateComponent,
    resolve: {
      personalInfo: PersonalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonalInfoExtUpdateComponent,
    resolve: {
      personalInfo: PersonalInfoExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
];
