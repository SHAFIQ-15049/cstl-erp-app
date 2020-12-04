import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEducationalInfo, EducationalInfo } from 'app/shared/model/educational-info.model';
import { EducationalInfoExtService } from './educational-info-ext.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import {EducationalInfoUpdateComponent} from "app/entities/educational-info/educational-info-update.component";

@Component({
  selector: 'jhi-educational-info-update',
  templateUrl: './educational-info-ext-update.component.html',
})
export class EducationalInfoExtUpdateComponent extends EducationalInfoUpdateComponent implements OnInit {

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected educationalInfoService: EducationalInfoExtService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, educationalInfoService, employeeService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationalInfo }) => {
      this.updateForm(educationalInfo);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }


}
