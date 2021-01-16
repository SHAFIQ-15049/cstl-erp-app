import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOverTime } from 'app/shared/model/over-time.model';

@Component({
  selector: 'jhi-over-time-detail',
  templateUrl: './over-time-detail.component.html',
})
export class OverTimeDetailComponent implements OnInit {
  overTime: IOverTime | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ overTime }) => (this.overTime = overTime));
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
