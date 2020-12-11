import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPersonalInfo, PersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoExtService } from './personal-info-ext.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import {PersonalInfoUpdateComponent} from "app/entities/personal-info/personal-info-update.component";
import {PersonalInfoService} from "app/entities/personal-info/personal-info.service";
import {EmployeeService} from "app/entities/employee/employee.service";

@Component({
  selector: 'jhi-personal-info-update',
  templateUrl: './personal-info-ext-update.component.html',
})
export class PersonalInfoExtUpdateComponent extends PersonalInfoUpdateComponent implements OnInit {


  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected personalInfoService: PersonalInfoExtService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, personalInfoService, employeeService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalInfo }) => {
      personalInfo.name = personalInfo.name?personalInfo.name: personalInfo.employee.name;
      this.updateForm(personalInfo);
    });
  }
}
