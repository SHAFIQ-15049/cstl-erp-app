import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdCardManagement, IdCardManagement } from 'app/shared/model/id-card-management.model';
import { IdCardManagementService } from './id-card-management.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-id-card-management-update',
  templateUrl: './id-card-management-update.component.html',
})
export class IdCardManagementUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  issueDateDp: any;
  validTillDp: any;
  employeeId!: number;

  editForm = this.fb.group({
    id: [],
    cardNo: [],
    issueDate: [],
    ticketNo: [],
    validTill: [],
    employee: [],
  });

  constructor(
    protected idCardManagementService: IdCardManagementService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idCardManagement }) => {
      this.employeeId = +this.activatedRoute.snapshot.params['employeeId'];
      if (this.employeeId) {
        this.employeeService.find(this.employeeId).subscribe(employee => {
          idCardManagement.employee = employee.body;
          this.updateForm(idCardManagement);
        });
      } else {
        this.updateForm(idCardManagement);
      }
      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(idCardManagement: IIdCardManagement): void {
    this.editForm.patchValue({
      id: idCardManagement.id,
      cardNo: idCardManagement.cardNo,
      issueDate: idCardManagement.issueDate,
      ticketNo: idCardManagement.ticketNo,
      validTill: idCardManagement.validTill,
      employee: idCardManagement.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const idCardManagement = this.createFromForm();
    if (idCardManagement.id !== undefined) {
      this.subscribeToSaveResponse(this.idCardManagementService.update(idCardManagement));
    } else {
      this.subscribeToSaveResponse(this.idCardManagementService.create(idCardManagement));
    }
  }

  private createFromForm(): IIdCardManagement {
    return {
      ...new IdCardManagement(),
      id: this.editForm.get(['id'])!.value,
      cardNo: this.editForm.get(['cardNo'])!.value,
      issueDate: this.editForm.get(['issueDate'])!.value,
      ticketNo: this.editForm.get(['ticketNo'])!.value,
      validTill: this.editForm.get(['validTill'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdCardManagement>>): void {
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
