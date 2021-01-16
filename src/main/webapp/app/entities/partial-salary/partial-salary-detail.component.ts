import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPartialSalary } from 'app/shared/model/partial-salary.model';

@Component({
  selector: 'jhi-partial-salary-detail',
  templateUrl: './partial-salary-detail.component.html',
})
export class PartialSalaryDetailComponent implements OnInit {
  partialSalary: IPartialSalary | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partialSalary }) => (this.partialSalary = partialSalary));
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
