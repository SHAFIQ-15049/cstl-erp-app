import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobHistory } from 'app/shared/model/job-history.model';
import {JobHistoryService} from "app/entities/job-history/job-history.service";

type EntityResponseType = HttpResponse<IJobHistory>;
type EntityArrayResponseType = HttpResponse<IJobHistory[]>;

@Injectable({ providedIn: 'root' })
export class JobHistoryExtService extends JobHistoryService{
  public resourceUrl = SERVER_API_URL + 'api/job-histories';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
