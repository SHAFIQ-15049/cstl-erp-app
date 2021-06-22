import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    fatherOrHusband: [],
    address: [],
    sex: [],
    phoneNo: [],
    nationality: [],
    dateOfBirth: [],
    guardiansName: [],
    chassisNo: [],
    engineNo: [],
    yearsOfMfg: [],
    preRegnNo: [],
    poOrBank: [],
    voterIdNo: [],
    voterIdAttachment: [],
    voterIdAttachmentContentType: [],
    passportAttachment: [],
    passportAttachmentContentType: [],
    birthCertificateAttachment: [],
    birthCertificateAttachmentContentType: [],
    gassOrWaterOrElectricityBill: [],
    gassOrWaterOrElectricityBillContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      name: customer.name,
      fatherOrHusband: customer.fatherOrHusband,
      address: customer.address,
      sex: customer.sex,
      phoneNo: customer.phoneNo,
      nationality: customer.nationality,
      dateOfBirth: customer.dateOfBirth,
      guardiansName: customer.guardiansName,
      chassisNo: customer.chassisNo,
      engineNo: customer.engineNo,
      yearsOfMfg: customer.yearsOfMfg,
      preRegnNo: customer.preRegnNo,
      poOrBank: customer.poOrBank,
      voterIdNo: customer.voterIdNo,
      voterIdAttachment: customer.voterIdAttachment,
      voterIdAttachmentContentType: customer.voterIdAttachmentContentType,
      passportAttachment: customer.passportAttachment,
      passportAttachmentContentType: customer.passportAttachmentContentType,
      birthCertificateAttachment: customer.birthCertificateAttachment,
      birthCertificateAttachmentContentType: customer.birthCertificateAttachmentContentType,
      gassOrWaterOrElectricityBill: customer.gassOrWaterOrElectricityBill,
      gassOrWaterOrElectricityBillContentType: customer.gassOrWaterOrElectricityBillContentType,
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
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      fatherOrHusband: this.editForm.get(['fatherOrHusband'])!.value,
      address: this.editForm.get(['address'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      phoneNo: this.editForm.get(['phoneNo'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      guardiansName: this.editForm.get(['guardiansName'])!.value,
      chassisNo: this.editForm.get(['chassisNo'])!.value,
      engineNo: this.editForm.get(['engineNo'])!.value,
      yearsOfMfg: this.editForm.get(['yearsOfMfg'])!.value,
      preRegnNo: this.editForm.get(['preRegnNo'])!.value,
      poOrBank: this.editForm.get(['poOrBank'])!.value,
      voterIdNo: this.editForm.get(['voterIdNo'])!.value,
      voterIdAttachmentContentType: this.editForm.get(['voterIdAttachmentContentType'])!.value,
      voterIdAttachment: this.editForm.get(['voterIdAttachment'])!.value,
      passportAttachmentContentType: this.editForm.get(['passportAttachmentContentType'])!.value,
      passportAttachment: this.editForm.get(['passportAttachment'])!.value,
      birthCertificateAttachmentContentType: this.editForm.get(['birthCertificateAttachmentContentType'])!.value,
      birthCertificateAttachment: this.editForm.get(['birthCertificateAttachment'])!.value,
      gassOrWaterOrElectricityBillContentType: this.editForm.get(['gassOrWaterOrElectricityBillContentType'])!.value,
      gassOrWaterOrElectricityBill: this.editForm.get(['gassOrWaterOrElectricityBill'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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
}
