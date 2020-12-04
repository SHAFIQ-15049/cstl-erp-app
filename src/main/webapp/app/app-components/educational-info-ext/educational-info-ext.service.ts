import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEducationalInfo } from 'app/shared/model/educational-info.model';
import {EducationalInfoService} from "app/entities/educational-info/educational-info.service";

type EntityResponseType = HttpResponse<IEducationalInfo>;
type EntityArrayResponseType = HttpResponse<IEducationalInfo[]>;

@Injectable({ providedIn: 'root' })
export class EducationalInfoExtService extends EducationalInfoService{
  public resourceUrl = SERVER_API_URL + 'api/educational-infos';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
