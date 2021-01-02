import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';

type EntityResponseType = HttpResponse<IFestivalAllowanceTimeLine>;
type EntityArrayResponseType = HttpResponse<IFestivalAllowanceTimeLine[]>;

@Injectable({ providedIn: 'root' })
export class FestivalAllowanceTimeLineService {
  public resourceUrl = SERVER_API_URL + 'api/festival-allowance-time-lines';

  constructor(protected http: HttpClient) {}

  create(festivalAllowanceTimeLine: IFestivalAllowanceTimeLine): Observable<EntityResponseType> {
    return this.http.post<IFestivalAllowanceTimeLine>(this.resourceUrl, festivalAllowanceTimeLine, { observe: 'response' });
  }

  update(festivalAllowanceTimeLine: IFestivalAllowanceTimeLine): Observable<EntityResponseType> {
    return this.http.put<IFestivalAllowanceTimeLine>(this.resourceUrl, festivalAllowanceTimeLine, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFestivalAllowanceTimeLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFestivalAllowanceTimeLine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
