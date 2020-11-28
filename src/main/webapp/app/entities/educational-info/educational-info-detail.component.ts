import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEducationalInfo } from 'app/shared/model/educational-info.model';

@Component({
  selector: 'jhi-educational-info-detail',
  templateUrl: './educational-info-detail.component.html',
})
export class EducationalInfoDetailComponent implements OnInit {
  educationalInfo: IEducationalInfo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationalInfo }) => (this.educationalInfo = educationalInfo));
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
