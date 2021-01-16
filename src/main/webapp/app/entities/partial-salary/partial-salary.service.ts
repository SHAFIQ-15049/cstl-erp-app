import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartialSalary } from 'app/shared/model/partial-salary.model';

type EntityResponseType = HttpResponse<IPartialSalary>;
type EntityArrayResponseType = HttpResponse<IPartialSalary[]>;

@Injectable({ providedIn: 'root' })
export class PartialSalaryService {
  public resourceUrl = SERVER_API_URL + 'api/partial-salaries';

  constructor(protected http: HttpClient) {}

  create(partialSalary: IPartialSalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partialSalary);
    return this.http
      .post<IPartialSalary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partialSalary: IPartialSalary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partialSalary);
    return this.http
      .put<IPartialSalary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartialSalary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartialSalary[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partialSalary: IPartialSalary): IPartialSalary {
    const copy: IPartialSalary = Object.assign({}, partialSalary, {
      fromDate: partialSalary.fromDate && partialSalary.fromDate.isValid() ? partialSalary.fromDate.toJSON() : undefined,
      toDate: partialSalary.toDate && partialSalary.toDate.isValid() ? partialSalary.toDate.toJSON() : undefined,
      executedOn: partialSalary.executedOn && partialSalary.executedOn.isValid() ? partialSalary.executedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
      res.body.executedOn = res.body.executedOn ? moment(res.body.executedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partialSalary: IPartialSalary) => {
        partialSalary.fromDate = partialSalary.fromDate ? moment(partialSalary.fromDate) : undefined;
        partialSalary.toDate = partialSalary.toDate ? moment(partialSalary.toDate) : undefined;
        partialSalary.executedOn = partialSalary.executedOn ? moment(partialSalary.executedOn) : undefined;
      });
    }
    return res;
  }
}
