import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';

@Component({
  selector: 'jhi-employee-salary-detail',
  templateUrl: './employee-salary-detail.component.html',
})
export class EmployeeSalaryDetailComponent implements OnInit {
  employeeSalary: IEmployeeSalary | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeSalary }) => (this.employeeSalary = employeeSalary));
  }

  previousState(): void {
    window.history.back();
  }
}
