import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';

import { AttendanceDataUpload, IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { AttendanceDataUploadService } from './attendance-data-upload.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-attendance-data-upload-update',
  templateUrl: './attendance-data-upload-update.component.html',
})
export class AttendanceDataUploadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fileUpload: [null, [Validators.required]],
    fileUploadContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected attendanceDataUploadService: AttendanceDataUploadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendanceDataUpload }) => {
      this.updateForm(attendanceDataUpload);
    });
  }

  updateForm(attendanceDataUpload: IAttendanceDataUpload): void {
    this.editForm.patchValue({
      id: attendanceDataUpload.id,
      fileUpload: attendanceDataUpload.fileUpload,
      fileUploadContentType: attendanceDataUpload.fileUploadContentType,
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
    const attendanceDataUpload = this.createFromForm();
    if (attendanceDataUpload.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceDataUploadService.update(attendanceDataUpload));
    } else {
      this.subscribeToSaveResponse(this.attendanceDataUploadService.create(attendanceDataUpload));
    }
  }

  private createFromForm(): IAttendanceDataUpload {
    return {
      ...new AttendanceDataUpload(),
      id: this.editForm.get(['id'])!.value,
      fileUploadContentType: this.editForm.get(['fileUploadContentType'])!.value,
      fileUpload: this.editForm.get(['fileUpload'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendanceDataUpload>>): void {
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
