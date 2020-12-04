import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAddress } from 'app/shared/model/address.model';
import {AddressService} from "app/entities/address/address.service";

type EntityResponseType = HttpResponse<IAddress>;
type EntityArrayResponseType = HttpResponse<IAddress[]>;

@Injectable({ providedIn: 'root' })
export class AddressExtService extends AddressService{
  public resourceUrl = SERVER_API_URL + 'api/addresses';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
