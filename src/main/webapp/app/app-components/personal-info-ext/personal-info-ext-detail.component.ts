import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import {PersonalInfoDetailComponent} from "app/entities/personal-info/personal-info-detail.component";
import {EmployeeExtService} from "app/app-components/employee-ext/employee-ext.service";

@Component({
  selector: 'jhi-personal-info-detail',
  templateUrl: './personal-info-ext-detail.component.html',
})
export class PersonalInfoExtDetailComponent extends PersonalInfoDetailComponent implements OnInit {
  employeeId?: number | null;
  showLoader?: boolean;

  constructor(protected dataUtils: JhiDataUtils,
              protected activatedRoute: ActivatedRoute,
              private employeeService: EmployeeExtService,
              private route: Router) {
    super(dataUtils, activatedRoute);
  }
  ngOnInit(): void {
    this.showLoader = true;
    this.activatedRoute.data.subscribe(({ personalInfo }) => {
      this.personalInfo = personalInfo
      this.employeeId = this.personalInfo?.employee?.id;
      if(!this.personalInfo?.id && this.employeeId){
        setInterval(()=>{
          this.route.navigate(['../../../personal-info',this.employeeId, 'new'], {relativeTo: this.activatedRoute});
        },2000);
      }else{
        this.showLoader = false;
      }
    });
  }

}
