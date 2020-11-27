import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEducationalInfo } from 'app/shared/model/educational-info.model';

type EntityResponseType = HttpResponse<IEducationalInfo>;
type EntityArrayResponseType = HttpResponse<IEducationalInfo[]>;

@Injectable({ providedIn: 'root' })
export class EducationalInfoService {
  public resourceUrl = SERVER_API_URL + 'api/educational-infos';

  constructor(protected http: HttpClient) {}

  create(educationalInfo: IEducationalInfo): Observable<EntityResponseType> {
    return this.http.post<IEducationalInfo>(this.resourceUrl, educationalInfo, { observe: 'response' });
  }

  update(educationalInfo: IEducationalInfo): Observable<EntityResponseType> {
    return this.http.put<IEducationalInfo>(this.resourceUrl, educationalInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEducationalInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEducationalInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
