import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';

@Component({
  selector: 'jhi-monthly-salary-dtl-detail',
  templateUrl: './monthly-salary-dtl-detail.component.html',
})
export class MonthlySalaryDtlDetailComponent implements OnInit {
  monthlySalaryDtl: IMonthlySalaryDtl | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlySalaryDtl }) => (this.monthlySalaryDtl = monthlySalaryDtl));
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
