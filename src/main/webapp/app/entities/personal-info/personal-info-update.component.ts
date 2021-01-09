import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPersonalInfo, PersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoService } from './personal-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-personal-info-update',
  templateUrl: './personal-info-update.component.html',
})
export class PersonalInfoUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    banglaName: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    photoId: [],
    fatherName: [null, [Validators.required]],
    fatherNameBangla: [],
    motherName: [null, [Validators.required]],
    motherNameBangla: [],
    maritalStatus: [],
    spouseName: [],
    spouseNameBangla: [],
    dateOfBirth: [],
    nationalId: [],
    nationalIdAttachment: [],
    nationalIdAttachmentContentType: [],
    nationalIdAttachmentId: [],
    birthRegistration: [],
    birthRegistrationAttachment: [],
    birthRegistrationAttachmentContentType: [],
    birthRegistrationAttachmentId: [],
    height: [],
    gender: [],
    religion: [],
    bloodGroup: [],
    emergencyContact: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected personalInfoService: PersonalInfoService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personalInfo }) => {
      this.updateForm(personalInfo);

      this.employeeService
        .query({ 'personalInfoId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IEmployee[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmployee[]) => {
          if (!personalInfo.employee || !personalInfo.employee.id) {
            this.employees = resBody;
          } else {
            this.employeeService
              .find(personalInfo.employee.id)
              .pipe(
                map((subRes: HttpResponse<IEmployee>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployee[]) => (this.employees = concatRes));
          }
        });
    });
  }

  updateForm(personalInfo: IPersonalInfo): void {
    this.editForm.patchValue({
      id: personalInfo.id,
      name: personalInfo.name,
      banglaName: personalInfo.banglaName,
      photo: personalInfo.photo,
      photoContentType: personalInfo.photoContentType,
      photoId: personalInfo.photoId,
      fatherName: personalInfo.fatherName,
      fatherNameBangla: personalInfo.fatherNameBangla,
      motherName: personalInfo.motherName,
      motherNameBangla: personalInfo.motherNameBangla,
      maritalStatus: personalInfo.maritalStatus,
      spouseName: personalInfo.spouseName,
      spouseNameBangla: personalInfo.spouseNameBangla,
      dateOfBirth: personalInfo.dateOfBirth,
      nationalId: personalInfo.nationalId,
      nationalIdAttachment: personalInfo.nationalIdAttachment,
      nationalIdAttachmentContentType: personalInfo.nationalIdAttachmentContentType,
      nationalIdAttachmentId: personalInfo.nationalIdAttachmentId,
      birthRegistration: personalInfo.birthRegistration,
      birthRegistrationAttachment: personalInfo.birthRegistrationAttachment,
      birthRegistrationAttachmentContentType: personalInfo.birthRegistrationAttachmentContentType,
      birthRegistrationAttachmentId: personalInfo.birthRegistrationAttachmentId,
      height: personalInfo.height,
      gender: personalInfo.gender,
      religion: personalInfo.religion,
      bloodGroup: personalInfo.bloodGroup,
      emergencyContact: personalInfo.emergencyContact,
      employee: personalInfo.employee,
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
      photoId: this.editForm.get(['photoId'])!.value,
      fatherName: this.editForm.get(['fatherName'])!.value,
      fatherNameBangla: this.editForm.get(['fatherNameBangla'])!.value,
      motherName: this.editForm.get(['motherName'])!.value,
      motherNameBangla: this.editForm.get(['motherNameBangla'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      spouseName: this.editForm.get(['spouseName'])!.value,
      spouseNameBangla: this.editForm.get(['spouseNameBangla'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      nationalId: this.editForm.get(['nationalId'])!.value,
      nationalIdAttachmentContentType: this.editForm.get(['nationalIdAttachmentContentType'])!.value,
      nationalIdAttachment: this.editForm.get(['nationalIdAttachment'])!.value,
      nationalIdAttachmentId: this.editForm.get(['nationalIdAttachmentId'])!.value,
      birthRegistration: this.editForm.get(['birthRegistration'])!.value,
      birthRegistrationAttachmentContentType: this.editForm.get(['birthRegistrationAttachmentContentType'])!.value,
      birthRegistrationAttachment: this.editForm.get(['birthRegistrationAttachment'])!.value,
      birthRegistrationAttachmentId: this.editForm.get(['birthRegistrationAttachmentId'])!.value,
      height: this.editForm.get(['height'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      religion: this.editForm.get(['religion'])!.value,
      bloodGroup: this.editForm.get(['bloodGroup'])!.value,
      emergencyContact: this.editForm.get(['emergencyContact'])!.value,
      employee: this.editForm.get(['employee'])!.value,
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

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }
}
