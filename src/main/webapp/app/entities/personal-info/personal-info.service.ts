import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonalInfo } from 'app/shared/model/personal-info.model';

type EntityResponseType = HttpResponse<IPersonalInfo>;
type EntityArrayResponseType = HttpResponse<IPersonalInfo[]>;

@Injectable({ providedIn: 'root' })
export class PersonalInfoService {
  public resourceUrl = SERVER_API_URL + 'api/personal-infos';

  constructor(protected http: HttpClient) {}

  create(personalInfo: IPersonalInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalInfo);
    return this.http
      .post<IPersonalInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personalInfo: IPersonalInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalInfo);
    return this.http
      .put<IPersonalInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonalInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonalInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personalInfo: IPersonalInfo): IPersonalInfo {
    const copy: IPersonalInfo = Object.assign({}, personalInfo, {
      dateOfBirth:
        personalInfo.dateOfBirth && personalInfo.dateOfBirth.isValid() ? personalInfo.dateOfBirth.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? moment(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personalInfo: IPersonalInfo) => {
        personalInfo.dateOfBirth = personalInfo.dateOfBirth ? moment(personalInfo.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
