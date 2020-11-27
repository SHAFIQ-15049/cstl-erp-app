import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceHistory } from 'app/shared/model/service-history.model';

@Component({
  selector: 'jhi-service-history-detail',
  templateUrl: './service-history-detail.component.html',
})
export class ServiceHistoryDetailComponent implements OnInit {
  serviceHistory: IServiceHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceHistory }) => (this.serviceHistory = serviceHistory));
  }

  previousState(): void {
    window.history.back();
  }
}
