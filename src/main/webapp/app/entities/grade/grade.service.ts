import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGrade } from 'app/shared/model/grade.model';

type EntityResponseType = HttpResponse<IGrade>;
type EntityArrayResponseType = HttpResponse<IGrade[]>;

@Injectable({ providedIn: 'root' })
export class GradeService {
  public resourceUrl = SERVER_API_URL + 'api/grades';

  constructor(protected http: HttpClient) {}

  create(grade: IGrade): Observable<EntityResponseType> {
    return this.http.post<IGrade>(this.resourceUrl, grade, { observe: 'response' });
  }

  update(grade: IGrade): Observable<EntityResponseType> {
    return this.http.put<IGrade>(this.resourceUrl, grade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
