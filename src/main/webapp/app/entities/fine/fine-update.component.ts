import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFine, Fine } from 'app/shared/model/fine.model';
import { FineService } from './fine.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-fine-update',
  templateUrl: './fine-update.component.html',
})
export class FineUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  finedOnDp: any;

  editForm = this.fb.group({
    id: [],
    finedOn: [null, [Validators.required]],
    reason: [],
    amount: [null, [Validators.required]],
    finePercentage: [null, [Validators.required]],
    monthlyFineAmount: [null, [Validators.required]],
    paymentStatus: [],
    amountPaid: [],
    amountLeft: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fineService: FineService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fine }) => {
      this.updateForm(fine);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(fine: IFine): void {
    this.editForm.patchValue({
      id: fine.id,
      finedOn: fine.finedOn,
      reason: fine.reason,
      amount: fine.amount,
      finePercentage: fine.finePercentage,
      monthlyFineAmount: fine.monthlyFineAmount,
      paymentStatus: fine.paymentStatus,
      amountPaid: fine.amountPaid,
      amountLeft: fine.amountLeft,
      employee: fine.employee,
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
    const fine = this.createFromForm();
    if (fine.id !== undefined) {
      this.subscribeToSaveResponse(this.fineService.update(fine));
    } else {
      this.subscribeToSaveResponse(this.fineService.create(fine));
    }
  }

  private createFromForm(): IFine {
    return {
      ...new Fine(),
      id: this.editForm.get(['id'])!.value,
      finedOn: this.editForm.get(['finedOn'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      finePercentage: this.editForm.get(['finePercentage'])!.value,
      monthlyFineAmount: this.editForm.get(['monthlyFineAmount'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      amountPaid: this.editForm.get(['amountPaid'])!.value,
      amountLeft: this.editForm.get(['amountLeft'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFine>>): void {
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
