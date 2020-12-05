import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILine } from 'app/shared/model/line.model';
import {LineService} from "app/entities/line/line.service";

type EntityResponseType = HttpResponse<ILine>;
type EntityArrayResponseType = HttpResponse<ILine[]>;

@Injectable({ providedIn: 'root' })
export class LineExtService extends LineService{
  public resourceUrl = SERVER_API_URL + 'api/lines';

  constructor(protected http: HttpClient) {
    super(http);
  }
}
