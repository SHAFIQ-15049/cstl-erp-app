import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEducationalInfo, EducationalInfo } from 'app/shared/model/educational-info.model';
import { EducationalInfoService } from './educational-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-educational-info-update',
  templateUrl: './educational-info-update.component.html',
})
export class EducationalInfoUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    serial: [null, [Validators.required]],
    degree: [null, [Validators.required]],
    institution: [null, [Validators.required]],
    passingYear: [null, [Validators.required]],
    courseDuration: [],
    attachment: [],
    attachmentContentType: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected educationalInfoService: EducationalInfoService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationalInfo }) => {
      this.updateForm(educationalInfo);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(educationalInfo: IEducationalInfo): void {
    this.editForm.patchValue({
      id: educationalInfo.id,
      serial: educationalInfo.serial,
      degree: educationalInfo.degree,
      institution: educationalInfo.institution,
      passingYear: educationalInfo.passingYear,
      courseDuration: educationalInfo.courseDuration,
      attachment: educationalInfo.attachment,
      attachmentContentType: educationalInfo.attachmentContentType,
      employee: educationalInfo.employee,
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
    const educationalInfo = this.createFromForm();
    if (educationalInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.educationalInfoService.update(educationalInfo));
    } else {
      this.subscribeToSaveResponse(this.educationalInfoService.create(educationalInfo));
    }
  }

  private createFromForm(): IEducationalInfo {
    return {
      ...new EducationalInfo(),
      id: this.editForm.get(['id'])!.value,
      serial: this.editForm.get(['serial'])!.value,
      degree: this.editForm.get(['degree'])!.value,
      institution: this.editForm.get(['institution'])!.value,
      passingYear: this.editForm.get(['passingYear'])!.value,
      courseDuration: this.editForm.get(['courseDuration'])!.value,
      attachmentContentType: this.editForm.get(['attachmentContentType'])!.value,
      attachment: this.editForm.get(['attachment'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducationalInfo>>): void {
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
