import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDutyLeave } from 'app/shared/model/duty-leave.model';

@Component({
  selector: 'jhi-duty-leave-detail',
  templateUrl: './duty-leave-detail.component.html',
})
export class DutyLeaveDetailComponent implements OnInit {
  dutyLeave: IDutyLeave | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dutyLeave }) => (this.dutyLeave = dutyLeave));
  }

  previousState(): void {
    window.history.back();
  }
}
