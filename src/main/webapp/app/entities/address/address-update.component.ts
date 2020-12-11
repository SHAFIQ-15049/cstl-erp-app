import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IDivision } from 'app/shared/model/division.model';
import { DivisionService } from 'app/entities/division/division.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';
import { IThana } from 'app/shared/model/thana.model';
import { ThanaService } from 'app/entities/thana/thana.service';

type SelectableEntity = IEmployee | IDivision | IDistrict | IThana;

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  divisions: IDivision[] = [];
  districts: IDistrict[] = [];
  thanas: IThana[] = [];

  editForm = this.fb.group({
    id: [],
    presentThanaTxt: [],
    presentStreet: [],
    presentStreetBangla: [],
    presentArea: [],
    presentAreaBangla: [],
    presentPostCode: [],
    presentPostCodeBangla: [],
    permanentThanaTxt: [],
    permanentStreet: [],
    permanentStreetBangla: [],
    permanentArea: [],
    permanentAreaBangla: [],
    permanentPostCode: [],
    permenentPostCodeBangla: [],
    isSame: [],
    employee: [],
    presentDivision: [],
    presentDistrict: [],
    presentThana: [],
    permanentDivision: [],
    permanentDistrict: [],
    permanentThana: [],
  });

  constructor(
    protected addressService: AddressService,
    protected employeeService: EmployeeService,
    protected divisionService: DivisionService,
    protected districtService: DistrictService,
    protected thanaService: ThanaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.employeeService
        .query({ 'addressId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IEmployee[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmployee[]) => {
          if (!address.employee || !address.employee.id) {
            this.employees = resBody;
          } else {
            this.employeeService
              .find(address.employee.id)
              .pipe(
                map((subRes: HttpResponse<IEmployee>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployee[]) => (this.employees = concatRes));
          }
        });

      this.divisionService.query().subscribe((res: HttpResponse<IDivision[]>) => (this.divisions = res.body || []));

      this.districtService.query().subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));

      this.thanaService.query().subscribe((res: HttpResponse<IThana[]>) => (this.thanas = res.body || []));
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      presentThanaTxt: address.presentThanaTxt,
      presentStreet: address.presentStreet,
      presentStreetBangla: address.presentStreetBangla,
      presentArea: address.presentArea,
      presentAreaBangla: address.presentAreaBangla,
      presentPostCode: address.presentPostCode,
      presentPostCodeBangla: address.presentPostCodeBangla,
      permanentThanaTxt: address.permanentThanaTxt,
      permanentStreet: address.permanentStreet,
      permanentStreetBangla: address.permanentStreetBangla,
      permanentArea: address.permanentArea,
      permanentAreaBangla: address.permanentAreaBangla,
      permanentPostCode: address.permanentPostCode,
      permenentPostCodeBangla: address.permenentPostCodeBangla,
      isSame: address.isSame,
      employee: address.employee,
      presentDivision: address.presentDivision,
      presentDistrict: address.presentDistrict,
      presentThana: address.presentThana,
      permanentDivision: address.permanentDivision,
      permanentDistrict: address.permanentDistrict,
      permanentThana: address.permanentThana,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      presentThanaTxt: this.editForm.get(['presentThanaTxt'])!.value,
      presentStreet: this.editForm.get(['presentStreet'])!.value,
      presentStreetBangla: this.editForm.get(['presentStreetBangla'])!.value,
      presentArea: this.editForm.get(['presentArea'])!.value,
      presentAreaBangla: this.editForm.get(['presentAreaBangla'])!.value,
      presentPostCode: this.editForm.get(['presentPostCode'])!.value,
      presentPostCodeBangla: this.editForm.get(['presentPostCodeBangla'])!.value,
      permanentThanaTxt: this.editForm.get(['permanentThanaTxt'])!.value,
      permanentStreet: this.editForm.get(['permanentStreet'])!.value,
      permanentStreetBangla: this.editForm.get(['permanentStreetBangla'])!.value,
      permanentArea: this.editForm.get(['permanentArea'])!.value,
      permanentAreaBangla: this.editForm.get(['permanentAreaBangla'])!.value,
      permanentPostCode: this.editForm.get(['permanentPostCode'])!.value,
      permenentPostCodeBangla: this.editForm.get(['permenentPostCodeBangla'])!.value,
      isSame: this.editForm.get(['isSame'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      presentDivision: this.editForm.get(['presentDivision'])!.value,
      presentDistrict: this.editForm.get(['presentDistrict'])!.value,
      presentThana: this.editForm.get(['presentThana'])!.value,
      permanentDivision: this.editForm.get(['permanentDivision'])!.value,
      permanentDistrict: this.editForm.get(['permanentDistrict'])!.value,
      permanentThana: this.editForm.get(['permanentThana'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
