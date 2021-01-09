import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';

@Component({
  selector: 'jhi-leave-application-detail',
  templateUrl: './leave-application-detail.component.html',
})
export class LeaveApplicationDetailComponent implements OnInit {
  leaveApplication: ILeaveApplication | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveApplication }) => (this.leaveApplication = leaveApplication));
  }

  previousState(): void {
    window.history.back();
  }
}
