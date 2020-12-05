import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILine, Line } from 'app/shared/model/line.model';
import { LineService } from './line.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'jhi-line-update',
  templateUrl: './line-update.component.html',
})
export class LineUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    department: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected lineService: LineService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ line }) => {
      this.updateForm(line);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  updateForm(line: ILine): void {
    this.editForm.patchValue({
      id: line.id,
      name: line.name,
      description: line.description,
      department: line.department,
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
    const line = this.createFromForm();
    if (line.id !== undefined) {
      this.subscribeToSaveResponse(this.lineService.update(line));
    } else {
      this.subscribeToSaveResponse(this.lineService.create(line));
    }
  }

  private createFromForm(): ILine {
    return {
      ...new Line(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      department: this.editForm.get(['department'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILine>>): void {
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

  trackById(index: number, item: IDepartment): any {
    return item.id;
  }
}
