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
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'jhi-monthly-salary-update',
  templateUrl: './monthly-salary-update.component.html',
})
export class MonthlySalaryUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
    fromDate: [],
    toDate: [],
    status: [],
    executedOn: [],
    executedBy: [],
    department: [],
  });

  constructor(
    protected monthlySalaryService: MonthlySalaryService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlySalary }) => {
      if (!monthlySalary.id) {
        const today = moment().startOf('day');
        monthlySalary.fromDate = today;
        monthlySalary.toDate = today;
        monthlySalary.executedOn = today;
      }

      this.updateForm(monthlySalary);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  updateForm(monthlySalary: IMonthlySalary): void {
    this.editForm.patchValue({
      id: monthlySalary.id,
      year: monthlySalary.year,
      month: monthlySalary.month,
      fromDate: monthlySalary.fromDate ? monthlySalary.fromDate.format(DATE_TIME_FORMAT) : null,
      toDate: monthlySalary.toDate ? monthlySalary.toDate.format(DATE_TIME_FORMAT) : null,
      status: monthlySalary.status,
      executedOn: monthlySalary.executedOn ? monthlySalary.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: monthlySalary.executedBy,
      department: monthlySalary.department,
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
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      toDate: this.editForm.get(['toDate'])!.value ? moment(this.editForm.get(['toDate'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value,
      department: this.editForm.get(['department'])!.value,
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

  trackById(index: number, item: IDepartment): any {
    return item.id;
  }
}
