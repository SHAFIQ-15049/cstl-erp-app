import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeekend } from 'app/shared/model/weekend.model';

type EntityResponseType = HttpResponse<IWeekend>;
type EntityArrayResponseType = HttpResponse<IWeekend[]>;

@Injectable({ providedIn: 'root' })
export class WeekendService {
  public resourceUrl = SERVER_API_URL + 'api/weekends';

  constructor(protected http: HttpClient) {}

  create(weekend: IWeekend): Observable<EntityResponseType> {
    return this.http.post<IWeekend>(this.resourceUrl, weekend, { observe: 'response' });
  }

  update(weekend: IWeekend): Observable<EntityResponseType> {
    return this.http.put<IWeekend>(this.resourceUrl, weekend, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWeekend>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWeekend[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
