import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import {PersonalInfoService} from "app/entities/personal-info/personal-info.service";

type EntityResponseType = HttpResponse<IPersonalInfo>;
type EntityArrayResponseType = HttpResponse<IPersonalInfo[]>;

@Injectable({ providedIn: 'root' })
export class PersonalInfoExtService extends PersonalInfoService{
  public resourceUrl = SERVER_API_URL + 'api/ext/personal-infos';

  constructor(protected http: HttpClient) {
    super(http);
  }



}
