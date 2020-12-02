import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { IDivision } from 'app/shared/model/division.model';
import { DivisionService } from 'app/entities/division/division.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';
import { IThana } from 'app/shared/model/thana.model';
import { ThanaService } from 'app/entities/thana/thana.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IDivision | IDistrict | IThana | IEmployee;

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  divisions: IDivision[] = [];
  districts: IDistrict[] = [];
  thanas: IThana[] = [];
  employees: IEmployee[] = [];
  employeeId?: number | null;

  addressForm = this.fb.group({
    addressArr : this.fb.array([

    ])
  });

  editForm = this.fb.group({
    id: [],
    street: [],
    area: [],
    postCode: [],
    addressType: [],
    division: [],
    district: [],
    thana: [],
    employee: [],
  });

  constructor(
    protected addressService: AddressService,
    protected divisionService: DivisionService,
    protected districtService: DistrictService,
    protected thanaService: ThanaService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.divisionService.query().subscribe((res: HttpResponse<IDivision[]>) => (this.divisions = res.body || []));

      this.districtService.query().subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));

      this.thanaService.query().subscribe((res: HttpResponse<IThana[]>) => (this.thanas = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      street: address.street,
      area: address.area,
      postCode: address.postCode,
      addressType: address.addressType,
      division: address.division,
      district: address.district,
      thana: address.thana,
      employee: address.employee,
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
      street: this.editForm.get(['street'])!.value,
      area: this.editForm.get(['area'])!.value,
      postCode: this.editForm.get(['postCode'])!.value,
      addressType: this.editForm.get(['addressType'])!.value,
      division: this.editForm.get(['division'])!.value,
      district: this.editForm.get(['district'])!.value,
      thana: this.editForm.get(['thana'])!.value,
      employee: this.editForm.get(['employee'])!.value,
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
