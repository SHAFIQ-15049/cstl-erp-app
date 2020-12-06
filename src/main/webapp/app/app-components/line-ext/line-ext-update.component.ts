import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILine, Line } from 'app/shared/model/line.model';
import { LineExtService } from './line-ext.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import {LineUpdateComponent} from "app/entities/line/line-update.component";

@Component({
  selector: 'jhi-line-update',
  templateUrl: './line-ext-update.component.html',
})
export class LineExtUpdateComponent extends LineUpdateComponent implements OnInit {


  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected lineService: LineExtService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, lineService, departmentService, activatedRoute, fb);
  }

}
