import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILine } from 'app/shared/model/line.model';
import {LineService} from "app/entities/line/line.service";
import {SessionStorageService} from "ngx-webstorage";

type EntityResponseType = HttpResponse<ILine>;
type EntityArrayResponseType = HttpResponse<ILine[]>;

@Injectable({ providedIn: 'root' })
export class LineExtService extends LineService{
  public resourceUrl = SERVER_API_URL + 'api/lines';
  private departmentIdKey = "lineDepartmentId";

  constructor(protected http: HttpClient, private $sessionStorage: SessionStorageService) {
    super(http);
  }

  public setDepartmentId(departmentId: number): void{
    this.$sessionStorage.store(this.departmentIdKey, departmentId);
  }

  public getDepartmentId(): number | null | undefined{
    return this.$sessionStorage.retrieve(this.departmentIdKey);
  }

  public clearDepartmentId(): void{
    this.$sessionStorage.clear(this.departmentIdKey);
  }
}
