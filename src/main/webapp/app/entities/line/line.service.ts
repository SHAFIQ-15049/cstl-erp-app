import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILine } from 'app/shared/model/line.model';

type EntityResponseType = HttpResponse<ILine>;
type EntityArrayResponseType = HttpResponse<ILine[]>;

@Injectable({ providedIn: 'root' })
export class LineService {
  public resourceUrl = SERVER_API_URL + 'api/lines';

  constructor(protected http: HttpClient) {}

  create(line: ILine): Observable<EntityResponseType> {
    return this.http.post<ILine>(this.resourceUrl, line, { observe: 'response' });
  }

  update(line: ILine): Observable<EntityResponseType> {
    return this.http.put<ILine>(this.resourceUrl, line, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
