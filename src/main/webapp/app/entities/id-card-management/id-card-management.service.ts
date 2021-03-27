import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIdCardManagement } from 'app/shared/model/id-card-management.model';

type EntityResponseType = HttpResponse<IIdCardManagement>;
type EntityArrayResponseType = HttpResponse<IIdCardManagement[]>;

@Injectable({ providedIn: 'root' })
export class IdCardManagementService {
  public resourceUrl = SERVER_API_URL + 'api/id-card-managements';

  constructor(protected http: HttpClient) {}

  create(idCardManagement: IIdCardManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(idCardManagement);
    return this.http
      .post<IIdCardManagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(idCardManagement: IIdCardManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(idCardManagement);
    return this.http
      .put<IIdCardManagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIdCardManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIdCardManagement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(idCardManagement: IIdCardManagement): IIdCardManagement {
    const copy: IIdCardManagement = Object.assign({}, idCardManagement, {
      issueDate:
        idCardManagement.issueDate && idCardManagement.issueDate.isValid() ? idCardManagement.issueDate.format(DATE_FORMAT) : undefined,
      validTill:
        idCardManagement.validTill && idCardManagement.validTill.isValid() ? idCardManagement.validTill.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.issueDate = res.body.issueDate ? moment(res.body.issueDate) : undefined;
      res.body.validTill = res.body.validTill ? moment(res.body.validTill) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((idCardManagement: IIdCardManagement) => {
        idCardManagement.issueDate = idCardManagement.issueDate ? moment(idCardManagement.issueDate) : undefined;
        idCardManagement.validTill = idCardManagement.validTill ? moment(idCardManagement.validTill) : undefined;
      });
    }
    return res;
  }
}
