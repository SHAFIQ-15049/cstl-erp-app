import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import {StateStorageService} from "app/core/auth/state-storage.service";

@Component({
  selector: 'jhi-employee-detail',
  templateUrl: './employee-detail.component.html',
})
export class EmployeeDetailComponent implements OnInit {
  employee: IEmployee | null = null;
  employeeId?: number | number;
  gotoEmployeeListRoute?: any;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, private router: Router, private stateStorageService: StateStorageService) {}

  ngOnInit(): void {
    this.gotoEmployeeListRoute = this.router.routerState.snapshot.url;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
      this.employeeId = this.employee?.id;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    this.router.navigateByUrl(this.stateStorageService.getCustomUrl()!);
  }

  gotoPersonalInfo():void{
    const employeeId = this.employeeId;
    if(this.employee?.personalInfo){
      this.router.navigate(['personal-info-employee', this.employee.personalInfo.id, 'view'])
    }else{
      this.router.navigate(['personal-info-employee', this.employeeId,'new'],{relativeTo: this.activatedRoute});
    }
  }

  gotoEducationalInfo(): void{

  }

  gotoJobHistory(): void{

  }

  gotoTraining():void{

  }

  gotoServiceHistory(): void{

  }

  gotoAddress():void{1
    if(this.employee?.address){
      this.router.navigate(['address-employee', this.employee.address.id, 'view'], {relativeTo: this.activatedRoute});
    }else{
      this.router.navigate(['address-employee', this.employeeId,'new'], {relativeTo: this.activatedRoute});
    }
  }
}
