import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingExtService } from './training-ext.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import {TrainingUpdateComponent} from "app/entities/training/training-update.component";

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-ext-update.component.html',
})
export class TrainingExtUpdateComponent extends TrainingUpdateComponent implements OnInit {

  constructor(
    protected trainingService: TrainingExtService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(trainingService, employeeService, activatedRoute, fb);
  }

}
