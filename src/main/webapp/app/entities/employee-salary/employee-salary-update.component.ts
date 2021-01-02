import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployeeSalary, EmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-employee-salary-update',
  templateUrl: './employee-salary-update.component.html',
})
export class EmployeeSalaryUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  salaryStartDateDp: any;
  salaryEndDateDp: any;
  nextIncrementDateDp: any;

  editForm = this.fb.group({
    id: [],
    gross: [],
    incrementAmount: [],
    incrementPercentage: [],
    salaryStartDate: [],
    salaryEndDate: [],
    nextIncrementDate: [],
    basic: [],
    basicPercent: [],
    houseRent: [],
    houseRentPercent: [],
    totalAllowance: [],
    medicalAllowance: [],
    medicalAllowancePercent: [],
    convinceAllowance: [],
    convinceAllowancePercent: [],
    foodAllowance: [],
    foodAllowancePercent: [],
    status: [],
    employee: [],
  });

  constructor(
    protected employeeSalaryService: EmployeeSalaryService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeSalary }) => {
      this.updateForm(employeeSalary);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(employeeSalary: IEmployeeSalary): void {
    this.editForm.patchValue({
      id: employeeSalary.id,
      gross: employeeSalary.gross,
      incrementAmount: employeeSalary.incrementAmount,
      incrementPercentage: employeeSalary.incrementPercentage,
      salaryStartDate: employeeSalary.salaryStartDate,
      salaryEndDate: employeeSalary.salaryEndDate,
      nextIncrementDate: employeeSalary.nextIncrementDate,
      basic: employeeSalary.basic,
      basicPercent: employeeSalary.basicPercent,
      houseRent: employeeSalary.houseRent,
      houseRentPercent: employeeSalary.houseRentPercent,
      totalAllowance: employeeSalary.totalAllowance,
      medicalAllowance: employeeSalary.medicalAllowance,
      medicalAllowancePercent: employeeSalary.medicalAllowancePercent,
      convinceAllowance: employeeSalary.convinceAllowance,
      convinceAllowancePercent: employeeSalary.convinceAllowancePercent,
      foodAllowance: employeeSalary.foodAllowance,
      foodAllowancePercent: employeeSalary.foodAllowancePercent,
      status: employeeSalary.status,
      employee: employeeSalary.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeSalary = this.createFromForm();
    if (employeeSalary.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeSalaryService.update(employeeSalary));
    } else {
      this.subscribeToSaveResponse(this.employeeSalaryService.create(employeeSalary));
    }
  }

  private createFromForm(): IEmployeeSalary {
    return {
      ...new EmployeeSalary(),
      id: this.editForm.get(['id'])!.value,
      gross: this.editForm.get(['gross'])!.value,
      incrementAmount: this.editForm.get(['incrementAmount'])!.value,
      incrementPercentage: this.editForm.get(['incrementPercentage'])!.value,
      salaryStartDate: this.editForm.get(['salaryStartDate'])!.value,
      salaryEndDate: this.editForm.get(['salaryEndDate'])!.value,
      nextIncrementDate: this.editForm.get(['nextIncrementDate'])!.value,
      basic: this.editForm.get(['basic'])!.value,
      basicPercent: this.editForm.get(['basicPercent'])!.value,
      houseRent: this.editForm.get(['houseRent'])!.value,
      houseRentPercent: this.editForm.get(['houseRentPercent'])!.value,
      totalAllowance: this.editForm.get(['totalAllowance'])!.value,
      medicalAllowance: this.editForm.get(['medicalAllowance'])!.value,
      medicalAllowancePercent: this.editForm.get(['medicalAllowancePercent'])!.value,
      convinceAllowance: this.editForm.get(['convinceAllowance'])!.value,
      convinceAllowancePercent: this.editForm.get(['convinceAllowancePercent'])!.value,
      foodAllowance: this.editForm.get(['foodAllowance'])!.value,
      foodAllowancePercent: this.editForm.get(['foodAllowancePercent'])!.value,
      status: this.editForm.get(['status'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSalary>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }
}
