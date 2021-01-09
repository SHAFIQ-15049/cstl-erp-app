import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

type EntityResponseType = HttpResponse<IAttendanceDataUpload>;
type EntityArrayResponseType = HttpResponse<IAttendanceDataUpload[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceDataUploadService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-data-uploads';

  constructor(protected http: HttpClient) {}

  create(attendanceDataUpload: IAttendanceDataUpload): Observable<EntityResponseType> {
    return this.http.post<IAttendanceDataUpload>(this.resourceUrl, attendanceDataUpload, { observe: 'response' });
  }

  update(attendanceDataUpload: IAttendanceDataUpload): Observable<EntityResponseType> {
    return this.http.put<IAttendanceDataUpload>(this.resourceUrl, attendanceDataUpload, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttendanceDataUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttendanceDataUpload[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
