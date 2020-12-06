import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDistrict, District } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';
import { IDivision } from 'app/shared/model/division.model';
import { DivisionService } from 'app/entities/division/division.service';

@Component({
  selector: 'jhi-district-update',
  templateUrl: './district-update.component.html',
})
export class DistrictUpdateComponent implements OnInit {
  isSaving = false;
  divisions: IDivision[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    bangla: [null, [Validators.required]],
    web: [],
    division: [],
  });

  constructor(
    protected districtService: DistrictService,
    protected divisionService: DivisionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ district }) => {
      this.updateForm(district);

      this.divisionService.query().subscribe((res: HttpResponse<IDivision[]>) => (this.divisions = res.body || []));
    });
  }

  updateForm(district: IDistrict): void {
    this.editForm.patchValue({
      id: district.id,
      name: district.name,
      bangla: district.bangla,
      web: district.web,
      division: district.division,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const district = this.createFromForm();
    if (district.id !== undefined) {
      this.subscribeToSaveResponse(this.districtService.update(district));
    } else {
      this.subscribeToSaveResponse(this.districtService.create(district));
    }
  }

  private createFromForm(): IDistrict {
    return {
      ...new District(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      bangla: this.editForm.get(['bangla'])!.value,
      web: this.editForm.get(['web'])!.value,
      division: this.editForm.get(['division'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrict>>): void {
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

  trackById(index: number, item: IDivision): any {
    return item.id;
  }
}
