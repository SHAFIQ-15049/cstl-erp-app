import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFestivalAllowanceTimeLine, FestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';
import { FestivalAllowanceTimeLineService } from './festival-allowance-time-line.service';

@Component({
  selector: 'jhi-festival-allowance-time-line-update',
  templateUrl: './festival-allowance-time-line-update.component.html',
})
export class FestivalAllowanceTimeLineUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
  });

  constructor(
    protected festivalAllowanceTimeLineService: FestivalAllowanceTimeLineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivalAllowanceTimeLine }) => {
      this.updateForm(festivalAllowanceTimeLine);
    });
  }

  updateForm(festivalAllowanceTimeLine: IFestivalAllowanceTimeLine): void {
    this.editForm.patchValue({
      id: festivalAllowanceTimeLine.id,
      year: festivalAllowanceTimeLine.year,
      month: festivalAllowanceTimeLine.month,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const festivalAllowanceTimeLine = this.createFromForm();
    if (festivalAllowanceTimeLine.id !== undefined) {
      this.subscribeToSaveResponse(this.festivalAllowanceTimeLineService.update(festivalAllowanceTimeLine));
    } else {
      this.subscribeToSaveResponse(this.festivalAllowanceTimeLineService.create(festivalAllowanceTimeLine));
    }
  }

  private createFromForm(): IFestivalAllowanceTimeLine {
    return {
      ...new FestivalAllowanceTimeLine(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFestivalAllowanceTimeLine>>): void {
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
