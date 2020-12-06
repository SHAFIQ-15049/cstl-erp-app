import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingService } from './training.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  receivedOnDp: any;

  editForm = this.fb.group({
    id: [],
    serial: [null, [Validators.required]],
    name: [null, [Validators.required]],
    trainingInstitute: [null, [Validators.required]],
    receivedOn: [],
    employee: [],
  });

  constructor(
    protected trainingService: TrainingService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.updateForm(training);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(training: ITraining): void {
    this.editForm.patchValue({
      id: training.id,
      serial: training.serial,
      name: training.name,
      trainingInstitute: training.trainingInstitute,
      receivedOn: training.receivedOn,
      employee: training.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.createFromForm();
    if (training.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  private createFromForm(): ITraining {
    return {
      ...new Training(),
      id: this.editForm.get(['id'])!.value,
      serial: this.editForm.get(['serial'])!.value,
      name: this.editForm.get(['name'])!.value,
      trainingInstitute: this.editForm.get(['trainingInstitute'])!.value,
      receivedOn: this.editForm.get(['receivedOn'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }
}
