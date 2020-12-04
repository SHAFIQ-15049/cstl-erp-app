import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPersonalInfo, PersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoService } from './personal-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-personal-info-update',
  templateUrl: './personal-info-update.component.html',
})
export class PersonalInfoUpdateComponent implements OnInit {
  isSaving = false;
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    banglaName: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    fatherName: [null, [Validators.required]],
    motherName: [null, [Validators.required]],
    maritalStatus: [],
    spouseName: [],
    dateOfBirth: [],
    nationalId: [],
    birthRegistration: [],
    height: [],
    gender: [],
    bloodGroup: [],
    emergencyContact: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected personalInfoService: PersonalInfoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalInfo }) => {
      this.updateForm(personalInfo);
    });
  }

  updateForm(personalInfo: IPersonalInfo): void {
    this.editForm.patchValue({
      id: personalInfo.id,
      name: personalInfo.name,
      banglaName: personalInfo.banglaName,
      photo: personalInfo.photo,
      photoContentType: personalInfo.photoContentType,
      fatherName: personalInfo.fatherName,
      motherName: personalInfo.motherName,
      maritalStatus: personalInfo.maritalStatus,
      spouseName: personalInfo.spouseName,
      dateOfBirth: personalInfo.dateOfBirth,
      nationalId: personalInfo.nationalId,
      birthRegistration: personalInfo.birthRegistration,
      height: personalInfo.height,
      gender: personalInfo.gender,
      bloodGroup: personalInfo.bloodGroup,
      emergencyContact: personalInfo.emergencyContact,
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
    const personalInfo = this.createFromForm();
    if (personalInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.personalInfoService.update(personalInfo));
    } else {
      this.subscribeToSaveResponse(this.personalInfoService.create(personalInfo));
    }
  }

  private createFromForm(): IPersonalInfo {
    return {
      ...new PersonalInfo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      banglaName: this.editForm.get(['banglaName'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      fatherName: this.editForm.get(['fatherName'])!.value,
      motherName: this.editForm.get(['motherName'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      spouseName: this.editForm.get(['spouseName'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      nationalId: this.editForm.get(['nationalId'])!.value,
      birthRegistration: this.editForm.get(['birthRegistration'])!.value,
      height: this.editForm.get(['height'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      bloodGroup: this.editForm.get(['bloodGroup'])!.value,
      emergencyContact: this.editForm.get(['emergencyContact'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonalInfo>>): void {
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
