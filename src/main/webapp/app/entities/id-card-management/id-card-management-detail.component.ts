import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdCardManagement } from 'app/shared/model/id-card-management.model';

@Component({
  selector: 'jhi-id-card-management-detail',
  templateUrl: './id-card-management-detail.component.html',
})
export class IdCardManagementDetailComponent implements OnInit {
  idCardManagement: IIdCardManagement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idCardManagement }) => (this.idCardManagement = idCardManagement));
  }

  previousState(): void {
    window.history.back();
  }
}
