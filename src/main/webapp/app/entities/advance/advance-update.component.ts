import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAdvance, Advance } from 'app/shared/model/advance.model';
import { AdvanceService } from './advance.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-advance-update',
  templateUrl: './advance-update.component.html',
})
export class AdvanceUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  providedOnDp: any;

  editForm = this.fb.group({
    id: [],
    providedOn: [null, [Validators.required]],
    reason: [],
    amount: [null, [Validators.required]],
    paymentPercentage: [null, [Validators.required]],
    monthlyPaymentAmount: [null, [Validators.required]],
    paymentStatus: [],
    amountPaid: [],
    amountLeft: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected advanceService: AdvanceService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advance }) => {
      this.updateForm(advance);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(advance: IAdvance): void {
    this.editForm.patchValue({
      id: advance.id,
      providedOn: advance.providedOn,
      reason: advance.reason,
      amount: advance.amount,
      paymentPercentage: advance.paymentPercentage,
      monthlyPaymentAmount: advance.monthlyPaymentAmount,
      paymentStatus: advance.paymentStatus,
      amountPaid: advance.amountPaid,
      amountLeft: advance.amountLeft,
      employee: advance.employee,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('codeNodeErpApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const advance = this.createFromForm();
    if (advance.id !== undefined) {
      this.subscribeToSaveResponse(this.advanceService.update(advance));
    } else {
      this.subscribeToSaveResponse(this.advanceService.create(advance));
    }
  }

  private createFromForm(): IAdvance {
    return {
      ...new Advance(),
      id: this.editForm.get(['id'])!.value,
      providedOn: this.editForm.get(['providedOn'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      paymentPercentage: this.editForm.get(['paymentPercentage'])!.value,
      monthlyPaymentAmount: this.editForm.get(['monthlyPaymentAmount'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      amountPaid: this.editForm.get(['amountPaid'])!.value,
      amountLeft: this.editForm.get(['amountLeft'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvance>>): void {
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
