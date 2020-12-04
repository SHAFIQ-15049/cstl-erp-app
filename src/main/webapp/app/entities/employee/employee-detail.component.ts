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

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }


}
