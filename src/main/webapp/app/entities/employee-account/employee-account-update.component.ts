import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployeeAccount, EmployeeAccount } from 'app/shared/model/employee-account.model';
import { EmployeeAccountService } from './employee-account.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-employee-account-update',
  templateUrl: './employee-account-update.component.html',
})
export class EmployeeAccountUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    accountType: [null, [Validators.required]],
    accountNo: [null, [Validators.required]],
    isSalaryAccount: [],
    employee: [],
  });

  constructor(
    protected employeeAccountService: EmployeeAccountService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeAccount }) => {
      this.updateForm(employeeAccount);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(employeeAccount: IEmployeeAccount): void {
    this.editForm.patchValue({
      id: employeeAccount.id,
      accountType: employeeAccount.accountType,
      accountNo: employeeAccount.accountNo,
      isSalaryAccount: employeeAccount.isSalaryAccount,
      employee: employeeAccount.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeAccount = this.createFromForm();
    if (employeeAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeAccountService.update(employeeAccount));
    } else {
      this.subscribeToSaveResponse(this.employeeAccountService.create(employeeAccount));
    }
  }

  private createFromForm(): IEmployeeAccount {
    return {
      ...new EmployeeAccount(),
      id: this.editForm.get(['id'])!.value,
      accountType: this.editForm.get(['accountType'])!.value,
      accountNo: this.editForm.get(['accountNo'])!.value,
      isSalaryAccount: this.editForm.get(['isSalaryAccount'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeAccount>>): void {
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
