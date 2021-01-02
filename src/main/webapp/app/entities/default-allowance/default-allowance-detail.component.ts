import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDefaultAllowance } from 'app/shared/model/default-allowance.model';

@Component({
  selector: 'jhi-default-allowance-detail',
  templateUrl: './default-allowance-detail.component.html',
})
export class DefaultAllowanceDetailComponent implements OnInit {
  defaultAllowance: IDefaultAllowance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ defaultAllowance }) => (this.defaultAllowance = defaultAllowance));
  }

  previousState(): void {
    window.history.back();
  }
}
