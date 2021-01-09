import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoliday } from 'app/shared/model/holiday.model';

@Component({
  selector: 'jhi-holiday-detail',
  templateUrl: './holiday-detail.component.html',
})
export class HolidayDetailComponent implements OnInit {
  holiday: IHoliday | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ holiday }) => (this.holiday = holiday));
  }

  previousState(): void {
    window.history.back();
  }
}
