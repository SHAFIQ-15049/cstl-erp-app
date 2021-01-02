import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMonthlySalary, MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from './monthly-salary.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-monthly-salary-update',
  templateUrl: './monthly-salary-update.component.html',
})
export class MonthlySalaryUpdateComponent implements OnInit {
  isSaving = false;
  designations: IDesignation[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
    status: [],
    executedOn: [],
    executedBy: [],
    designation: [],
  });

  constructor(
    protected monthlySalaryService: MonthlySalaryService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlySalary }) => {
      if (!monthlySalary.id) {
        const today = moment().startOf('day');
        monthlySalary.executedOn = today;
        monthlySalary.executedBy = today;
      }

      this.updateForm(monthlySalary);

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    });
  }

  updateForm(monthlySalary: IMonthlySalary): void {
    this.editForm.patchValue({
      id: monthlySalary.id,
      year: monthlySalary.year,
      month: monthlySalary.month,
      status: monthlySalary.status,
      executedOn: monthlySalary.executedOn ? monthlySalary.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: monthlySalary.executedBy ? monthlySalary.executedBy.format(DATE_TIME_FORMAT) : null,
      designation: monthlySalary.designation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const monthlySalary = this.createFromForm();
    if (monthlySalary.id !== undefined) {
      this.subscribeToSaveResponse(this.monthlySalaryService.update(monthlySalary));
    } else {
      this.subscribeToSaveResponse(this.monthlySalaryService.create(monthlySalary));
    }
  }

  private createFromForm(): IMonthlySalary {
    return {
      ...new MonthlySalary(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      status: this.editForm.get(['status'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value ? moment(this.editForm.get(['executedBy'])!.value, DATE_TIME_FORMAT) : undefined,
      designation: this.editForm.get(['designation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlySalary>>): void {
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

  trackById(index: number, item: IDesignation): any {
    return item.id;
  }
}
