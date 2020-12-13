import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServiceHistory, ServiceHistory } from 'app/shared/model/service-history.model';
import { ServiceHistoryExtService } from './service-history-ext.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import {ServiceHistoryUpdateComponent} from "app/entities/service-history/service-history-update.component";
import {JhiDataUtils, JhiEventManager} from "ng-jhipster";
import {ServiceHistoryService} from "app/entities/service-history/service-history.service";

type SelectableEntity = IDepartment | IDesignation | IGrade | IEmployee;

@Component({
  selector: 'jhi-service-history-update',
  templateUrl: './service-history-ext-update.component.html',
})
export class ServiceHistoryExtUpdateComponent extends ServiceHistoryUpdateComponent implements OnInit {


  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected serviceHistoryService: ServiceHistoryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected gradeService: GradeService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, serviceHistoryService, departmentService, designationService, gradeService, employeeService, activatedRoute, fb);
  }


}
