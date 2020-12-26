import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWeekend, Weekend } from 'app/shared/model/weekend.model';
import { WeekendService } from './weekend.service';

@Component({
  selector: 'jhi-weekend-update',
  templateUrl: './weekend-update.component.html',
})
export class WeekendUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    day: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(protected weekendService: WeekendService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekend }) => {
      this.updateForm(weekend);
    });
  }

  updateForm(weekend: IWeekend): void {
    this.editForm.patchValue({
      id: weekend.id,
      day: weekend.day,
      status: weekend.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const weekend = this.createFromForm();
    if (weekend.id !== undefined) {
      this.subscribeToSaveResponse(this.weekendService.update(weekend));
    } else {
      this.subscribeToSaveResponse(this.weekendService.create(weekend));
    }
  }

  private createFromForm(): IWeekend {
    return {
      ...new Weekend(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeekend>>): void {
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
