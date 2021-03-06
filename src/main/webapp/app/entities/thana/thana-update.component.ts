import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IThana, Thana } from 'app/shared/model/thana.model';
import { ThanaService } from './thana.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';

@Component({
  selector: 'jhi-thana-update',
  templateUrl: './thana-update.component.html',
})
export class ThanaUpdateComponent implements OnInit {
  isSaving = false;
  districts: IDistrict[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    bangla: [null, [Validators.required]],
    web: [],
    district: [],
  });

  constructor(
    protected thanaService: ThanaService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thana }) => {
      this.updateForm(thana);

      this.districtService.query({ size: 10000 }).subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));
    });
  }

  updateForm(thana: IThana): void {
    this.editForm.patchValue({
      id: thana.id,
      name: thana.name,
      bangla: thana.bangla,
      web: thana.web,
      district: thana.district,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thana = this.createFromForm();
    if (thana.id !== undefined) {
      this.subscribeToSaveResponse(this.thanaService.update(thana));
    } else {
      this.subscribeToSaveResponse(this.thanaService.create(thana));
    }
  }

  private createFromForm(): IThana {
    return {
      ...new Thana(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      bangla: this.editForm.get(['bangla'])!.value,
      web: this.editForm.get(['web'])!.value,
      district: this.editForm.get(['district'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThana>>): void {
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

  trackById(index: number, item: IDistrict): any {
    return item.id;
  }
}
