import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

type EntityResponseType = HttpResponse<IAttendanceDataUpload>;

@Injectable({ providedIn: 'root' })
export class AttendanceDataUploadService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-data-uploads';

  constructor(protected http: HttpClient) {}

  create(attendanceDataUpload: IAttendanceDataUpload): Observable<EntityResponseType> {
    return this.http.post<IAttendanceDataUpload>(this.resourceUrl, attendanceDataUpload, { observe: 'response' });
  }
}
