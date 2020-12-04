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

type SelectableEntity = IDivision | IDistrict | IThana;

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-ext-update.component.html',
})
export class AddressExtUpdateComponent extends AddressUpdateComponent implements OnInit {

  constructor(
    protected addressService: AddressExtService,
    protected divisionService: DivisionService,
    protected districtService: DistrictService,
    protected thanaService: ThanaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(addressService, divisionService, districtService, thanaService, activatedRoute, fb);
  }

}
