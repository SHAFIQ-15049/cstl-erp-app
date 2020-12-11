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
import {LineService} from "app/entities/line/line.service";
import {ILine} from "app/shared/model/line.model";
import {EmployeeService} from "app/entities/employee/employee.service";

type SelectableEntity = IAddress | IPersonalInfo | ICompany | IDepartment | IGrade | IDesignation;

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-ext-update.component.html',
})
export class EmployeeExtUpdateComponent extends EmployeeUpdateComponent implements OnInit {


  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeService: EmployeeService,
    protected companyService: CompanyService,
    protected departmentService: DepartmentService,
    protected gradeService: GradeService,
    protected designationService: DesignationService,
    protected lineService: LineService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, employeeService,  companyService, departmentService, gradeService, designationService, lineService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
      this.categorySelected();
      this.departmentSelected();

      this.companyService.query({size:10000}).subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.departmentService.query({size:10000}).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));


    });
  }

  categorySelected():void{
    const category = this.editForm.get('category')?.value;
    if(category){
      this.designationService.query({
        size:10000,
        'category.equals': category,
      }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

      this.gradeService.query({
        size:10000,
        'category.equals': category,
      }).subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
    }

  }

  departmentSelected(): void{
    const department = this.editForm.get('department')?.value;
    if(department?.id){
      this.lineService.query({
        'departmentId.equals': department.id,
        size: 10000
      }).subscribe((res: HttpResponse<ILine[]>) => (this.lines = res.body || []));
    }
  }

}
