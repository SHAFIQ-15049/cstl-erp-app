import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveBalance } from 'app/shared/model/leave-balance.model';

@Component({
  selector: 'jhi-leave-balance-detail',
  templateUrl: './leave-balance-detail.component.html',
})
export class LeaveBalanceDetailComponent implements OnInit {
  leaveBalance: ILeaveBalance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveBalance }) => (this.leaveBalance = leaveBalance));
  }

  previousState(): void {
    window.history.back();
  }
}
