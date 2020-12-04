import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITraining } from 'app/shared/model/training.model';
import {TrainingService} from "app/entities/training/training.service";

type EntityResponseType = HttpResponse<ITraining>;
type EntityArrayResponseType = HttpResponse<ITraining[]>;

@Injectable({ providedIn: 'root' })
export class TrainingExtService extends TrainingService{
  public resourceUrl = SERVER_API_URL + 'api/trainings';

  constructor(protected http: HttpClient) {
    super(http);
  }

}
