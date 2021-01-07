import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';

@Component({
  selector: 'jhi-monthly-salary-detail',
  templateUrl: './monthly-salary-detail.component.html',
})
export class MonthlySalaryDetailComponent implements OnInit {
  monthlySalary: IMonthlySalary | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monthlySalary }) => (this.monthlySalary = monthlySalary));
  }

  previousState(): void {
    window.history.back();
  }
}
