import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { Holiday, IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from 'app/entities/holiday-type/holiday-type.service';

@Component({
  selector: 'jhi-holiday-update',
  templateUrl: './holiday-update.component.html',
})
export class HolidayUpdateComponent implements OnInit {
  isSaving = false;
  holidaytypes: IHolidayType[] = [];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [null, [Validators.required]],
    to: [null, [Validators.required]],
    totalDays: [null, [Validators.required]],
    holidayType: [null, Validators.required],
  });

  constructor(
    protected holidayService: HolidayService,
    protected holidayTypeService: HolidayTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ holiday }) => {
      this.updateForm(holiday);

      this.holidayTypeService.query().subscribe((res: HttpResponse<IHolidayType[]>) => (this.holidaytypes = res.body || []));
    });

    this.onChanges();
  }

  updateForm(holiday: IHoliday): void {
    this.editForm.patchValue({
      id: holiday.id,
      from: holiday.from,
      to: holiday.to,
      totalDays: holiday.totalDays,
      holidayType: holiday.holidayType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const holiday = this.createFromForm();
    if (holiday.id !== undefined) {
      this.subscribeToSaveResponse(this.holidayService.update(holiday));
    } else {
      this.subscribeToSaveResponse(this.holidayService.create(holiday));
    }
  }

  private createFromForm(): IHoliday {
    return {
      ...new Holiday(),
      id: this.editForm.get(['id'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      totalDays: this.editForm.get(['totalDays'])!.value,
      holidayType: this.editForm.get(['holidayType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoliday>>): void {
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

  trackById(index: number, item: IHolidayType): any {
    return item.id;
  }

  onChanges(): void {
    this.editForm.get('from')!.valueChanges.subscribe(() => {
      this.updateTotalDays();
    });

    this.editForm.get('to')!.valueChanges.subscribe(() => {
      this.updateTotalDays();
    });
  }

  private updateTotalDays(): void {
    const fromDate = new Date(this.editForm.get('from')!.value);
    const toDate = new Date(this.editForm.get('to')!.value);
    const diff = toDate.getTime() - fromDate.getTime();
    this.editForm.patchValue({
      totalDays: diff / (1000 * 60 * 60 * 24) + 1,
    });
  }
}
