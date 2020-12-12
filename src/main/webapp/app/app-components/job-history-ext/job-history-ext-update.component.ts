import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJobHistory, JobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryExtService } from './job-history-ext.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import {JobHistoryUpdateComponent} from "app/entities/job-history/job-history-update.component";

@Component({
  selector: 'jhi-job-history-update',
  templateUrl: './job-history-ext-update.component.html',
})
export class JobHistoryExtUpdateComponent extends JobHistoryUpdateComponent implements OnInit {

  constructor(
    protected jobHistoryService: JobHistoryExtService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(jobHistoryService, employeeService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobHistory }) => {
      this.updateForm(jobHistory);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  calculateTotalExperience(): void{
    if(this.editForm.get('from')?.value && this.editForm.get('to')?.value){
      const fromDate = this.editForm.get('from')?.value;
      const toDate = this.editForm.get('to')?.value;
      this.editForm.get('totalExperience')?.setValue(toDate.diff(fromDate,'years',true).toFixed(2));
    }
  }

}
