import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonalInfo } from 'app/shared/model/personal-info.model';

@Component({
  selector: 'jhi-personal-info-detail',
  templateUrl: './personal-info-detail.component.html',
})
export class PersonalInfoDetailComponent implements OnInit {
  personalInfo: IPersonalInfo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalInfo }) => (this.personalInfo = personalInfo));
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
