import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceHistory } from 'app/shared/model/service-history.model';
import {ServiceHistoryService} from "app/entities/service-history/service-history.service";

type EntityResponseType = HttpResponse<IServiceHistory>;
type EntityArrayResponseType = HttpResponse<IServiceHistory[]>;

@Injectable({ providedIn: 'root' })
export class ServiceHistoryExtService extends ServiceHistoryService{
  public resourceUrl = SERVER_API_URL + 'api/service-histories';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
