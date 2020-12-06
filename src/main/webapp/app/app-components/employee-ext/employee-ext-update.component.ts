import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeExtService } from './employee-ext.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoService } from 'app/entities/personal-info/personal-info.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import {EmployeeUpdateComponent} from "app/entities/employee/employee-update.component";

type SelectableEntity = IAddress | IPersonalInfo | ICompany | IDepartment | IGrade | IDesignation;

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-ext-update.component.html',
})
export class EmployeeExtUpdateComponent extends EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];
  personalinfos: IPersonalInfo[] = [];
  companies: ICompany[] = [];
  departments: IDepartment[] = [];
  grades: IGrade[] = [];
  designations: IDesignation[] = [];
  joiningDateDp: any;
  terminationDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    empId: [null, [Validators.required]],
    globalId: [null, [Validators.required]],
    localId: [null, [Validators.required]],
    category: [],
    type: [],
    joiningDate: [],
    status: [],
    terminationDate: [],
    terminationReason: [],
    address: [],
    personalInfo: [],
    company: [],
    department: [],
    grade: [],
    designation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeService: EmployeeExtService,
    protected addressService: AddressService,
    protected personalInfoService: PersonalInfoService,
    protected companyService: CompanyService,
    protected departmentService: DepartmentService,
    protected gradeService: GradeService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, employeeService, addressService, personalInfoService, companyService, departmentService, gradeService, designationService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.addressService
        .query({ 'employeeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!employee.address || !employee.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(employee.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });

      this.personalInfoService
        .query({ 'employeeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IPersonalInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPersonalInfo[]) => {
          if (!employee.personalInfo || !employee.personalInfo.id) {
            this.personalinfos = resBody;
          } else {
            this.personalInfoService
              .find(employee.personalInfo.id)
              .pipe(
                map((subRes: HttpResponse<IPersonalInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPersonalInfo[]) => (this.personalinfos = concatRes));
          }
        });

      this.companyService.query({size:10000}).subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.departmentService.query({size:10000}).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));


    });
  }

  categorySelected():void{
    const category = this.editForm.get('category')?.value;
    this.designationService.query({
      size:10000,
      'category.equals': category,
    }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

    this.gradeService.query({
      size:10000,
      'category.equals': category,
    }).subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
  }

  departmentSelected(): void{
    const departmentId = this.editForm.get('department')?.value;
  }

}
