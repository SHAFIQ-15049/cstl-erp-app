import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';

@Component({
  selector: 'jhi-festival-allowance-time-line-detail',
  templateUrl: './festival-allowance-time-line-detail.component.html',
})
export class FestivalAllowanceTimeLineDetailComponent implements OnInit {
  festivalAllowanceTimeLine: IFestivalAllowanceTimeLine | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivalAllowanceTimeLine }) => (this.festivalAllowanceTimeLine = festivalAllowanceTimeLine));
  }

  previousState(): void {
    window.history.back();
  }
}
