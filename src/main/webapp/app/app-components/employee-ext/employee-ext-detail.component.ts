import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { EmployeeDetailComponent } from 'app/entities/employee/employee-detail.component';
import { EmployeeExtService } from 'app/app-components/employee-ext/employee-ext.service';
import { UserService } from 'app/core/user/user.service';
import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-employee-detail',
  templateUrl: './employee-ext-detail.component.html',
})
export class EmployeeExtDetailComponent extends EmployeeDetailComponent implements OnInit {
  employee: IEmployee | null = null;
  employeeId?: number | number;
  gotoEmployeeListRoute?: any;
  user?: IUser;
  login?: string;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    private router: Router,
    private stateStorageService: StateStorageService,
    private employeeService: EmployeeExtService,
    private userService: UserService
  ) {
    super(dataUtils, activatedRoute);
  }

  ngOnInit(): void {
    this.gotoEmployeeListRoute = this.router.routerState.snapshot.url;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
      this.employeeId = this.employee?.id;
      this.employeeService.storeEmployeeId(this.employee?.id!);
      /*      if (this.employee?.localId) {
        this.userService.find(this.employee.localId).subscribe(res => {
          this.user = res;
        });
      }*/
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

  gotoPersonalInfo(): void {
    const employeeId = this.employeeId;
    this.router.navigate([{ outlets: { emp: ['personal-info', this.employeeId, 'employee-view'] } }], { relativeTo: this.activatedRoute });

    /* if(this.employee?.personalInfo){
      this.router.navigate([{outlets:{emp: ['personal-info', this.employee.personalInfo.id,'view']}}], {relativeTo: this.activatedRoute});
    }else{
      this.router.navigate([{outlets:{emp: ['personal-info', this.employeeId,'new']}}], {relativeTo: this.activatedRoute});
    }*/
  }

  gotoEducationalInfo(): void {
    this.router.navigate([{ outlets: { emp: ['educational-info'] } }], { relativeTo: this.activatedRoute });
  }

  gotoJobHistory(): void {
    this.router.navigate([{ outlets: { emp: ['job-history'] } }], { relativeTo: this.activatedRoute });
  }

  gotoTraining(): void {
    this.router.navigate([{ outlets: { emp: ['training'] } }], { relativeTo: this.activatedRoute });
  }

  gotoServiceHistory(): void {
    this.router.navigate([{ outlets: { emp: ['service-history'] } }], { relativeTo: this.activatedRoute });
  }

  gotoAddress(): void {
    if (this.employee?.address) {
      this.router.navigate([{ outlets: { emp: ['address-employee', this.employee.address.id, 'view'] } }], {
        relativeTo: this.activatedRoute,
      });
    } else {
      this.router.navigate([{ outlets: { emp: ['address-employee', this.employeeId, 'new'] } }], { relativeTo: this.activatedRoute });
    }
  }
}
