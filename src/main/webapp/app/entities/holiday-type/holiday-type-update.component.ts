import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHolidayType, HolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';

@Component({
  selector: 'jhi-holiday-type-update',
  templateUrl: './holiday-type-update.component.html',
})
export class HolidayTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected holidayTypeService: HolidayTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ holidayType }) => {
      this.updateForm(holidayType);
    });
  }

  updateForm(holidayType: IHolidayType): void {
    this.editForm.patchValue({
      id: holidayType.id,
      name: holidayType.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const holidayType = this.createFromForm();
    if (holidayType.id !== undefined) {
      this.subscribeToSaveResponse(this.holidayTypeService.update(holidayType));
    } else {
      this.subscribeToSaveResponse(this.holidayTypeService.create(holidayType));
    }
  }

  private createFromForm(): IHolidayType {
    return {
      ...new HolidayType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHolidayType>>): void {
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
