import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressExtService } from './address-ext.service';
import { IDivision } from 'app/shared/model/division.model';
import { DivisionService } from 'app/entities/division/division.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';
import { IThana } from 'app/shared/model/thana.model';
import { ThanaService } from 'app/entities/thana/thana.service';
import {AddressUpdateComponent} from "app/entities/address/address-update.component";
import {AddressService} from "app/entities/address/address.service";
import {EmployeeService} from "app/entities/employee/employee.service";
import {map} from "rxjs/operators";
import {IEmployee} from "app/shared/model/employee.model";

type SelectableEntity = IDivision | IDistrict | IThana;

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-ext-update.component.html',
})
export class AddressExtUpdateComponent extends AddressUpdateComponent implements OnInit {

  constructor(
    protected addressService: AddressService,
    protected employeeService: EmployeeService,
    protected divisionService: DivisionService,
    protected districtService: DistrictService,
    protected thanaService: ThanaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(addressService, employeeService, divisionService, districtService, thanaService, activatedRoute, fb);
  }

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

      this.divisionService.query({size:10000}).subscribe((res: HttpResponse<IDivision[]>) => (this.divisions = res.body || []));

      this.districtService.query({size:10000}).subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));

      this.thanaService.query({size:10000}).subscribe((res: HttpResponse<IThana[]>) => (this.thanas = res.body || []));
    });
  }

  makePresentAndPermanentAddressSame():void{
    if(this.editForm.get('isSame')!.value === true){
      this.editForm.get('permanentDivision')?.setValue(this.editForm.get('presentDivision')?.value);
      this.editForm.get('permanentDistrict')?.setValue(this.editForm.get('presentDistrict')?.value);
      this.editForm.get('permanentThana')?.setValue(this.editForm.get('presentThana')?.value);
      this.editForm.get('permanentStreet')?.setValue(this.editForm.get('presentStreet')?.value);
      this.editForm.get('permanentArea')?.setValue(this.editForm.get('presentArea')?.value);
      this.editForm.get('permanentDivision')?.setValue(this.editForm.get('presentDivision')?.value);

    }
  }
}
