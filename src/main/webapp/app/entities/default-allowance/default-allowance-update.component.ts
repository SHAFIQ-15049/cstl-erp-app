import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDefaultAllowance, DefaultAllowance } from 'app/shared/model/default-allowance.model';
import { DefaultAllowanceService } from './default-allowance.service';

@Component({
  selector: 'jhi-default-allowance-update',
  templateUrl: './default-allowance-update.component.html',
})
export class DefaultAllowanceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    basic: [],
    basicPercent: [],
    totalAllowance: [null, [Validators.required]],
    medicalAllowance: [],
    medicalAllowancePercent: [],
    convinceAllowance: [],
    convinceAllowancePercent: [],
    foodAllowance: [],
    foodAllowancePercent: [],
    festivalAllowance: [],
    festivalAllowancePercent: [],
    status: [null, [Validators.required]],
  });

  constructor(
    protected defaultAllowanceService: DefaultAllowanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ defaultAllowance }) => {
      this.updateForm(defaultAllowance);
    });
  }

  updateForm(defaultAllowance: IDefaultAllowance): void {
    this.editForm.patchValue({
      id: defaultAllowance.id,
      basic: defaultAllowance.basic,
      basicPercent: defaultAllowance.basicPercent,
      totalAllowance: defaultAllowance.totalAllowance,
      medicalAllowance: defaultAllowance.medicalAllowance,
      medicalAllowancePercent: defaultAllowance.medicalAllowancePercent,
      convinceAllowance: defaultAllowance.convinceAllowance,
      convinceAllowancePercent: defaultAllowance.convinceAllowancePercent,
      foodAllowance: defaultAllowance.foodAllowance,
      foodAllowancePercent: defaultAllowance.foodAllowancePercent,
      festivalAllowance: defaultAllowance.festivalAllowance,
      festivalAllowancePercent: defaultAllowance.festivalAllowancePercent,
      status: defaultAllowance.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const defaultAllowance = this.createFromForm();
    if (defaultAllowance.id !== undefined) {
      this.subscribeToSaveResponse(this.defaultAllowanceService.update(defaultAllowance));
    } else {
      this.subscribeToSaveResponse(this.defaultAllowanceService.create(defaultAllowance));
    }
  }

  private createFromForm(): IDefaultAllowance {
    return {
      ...new DefaultAllowance(),
      id: this.editForm.get(['id'])!.value,
      basic: this.editForm.get(['basic'])!.value,
      basicPercent: this.editForm.get(['basicPercent'])!.value,
      totalAllowance: this.editForm.get(['totalAllowance'])!.value,
      medicalAllowance: this.editForm.get(['medicalAllowance'])!.value,
      medicalAllowancePercent: this.editForm.get(['medicalAllowancePercent'])!.value,
      convinceAllowance: this.editForm.get(['convinceAllowance'])!.value,
      convinceAllowancePercent: this.editForm.get(['convinceAllowancePercent'])!.value,
      foodAllowance: this.editForm.get(['foodAllowance'])!.value,
      foodAllowancePercent: this.editForm.get(['foodAllowancePercent'])!.value,
      festivalAllowance: this.editForm.get(['festivalAllowance'])!.value,
      festivalAllowancePercent: this.editForm.get(['festivalAllowancePercent'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDefaultAllowance>>): void {
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
}
