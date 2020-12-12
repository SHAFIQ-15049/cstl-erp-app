import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IServiceHistory } from 'app/shared/model/service-history.model';

@Component({
  selector: 'jhi-service-history-detail',
  templateUrl: './service-history-detail.component.html',
})
export class ServiceHistoryDetailComponent implements OnInit {
  serviceHistory: IServiceHistory | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceHistory }) => (this.serviceHistory = serviceHistory));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
