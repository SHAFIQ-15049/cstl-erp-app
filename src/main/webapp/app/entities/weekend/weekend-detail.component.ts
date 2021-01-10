import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWeekend } from 'app/shared/model/weekend.model';

@Component({
  selector: 'jhi-weekend-detail',
  templateUrl: './weekend-detail.component.html',
})
export class WeekendDetailComponent implements OnInit {
  weekend: IWeekend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekend }) => (this.weekend = weekend));
  }

  previousState(): void {
    window.history.back();
  }
}
