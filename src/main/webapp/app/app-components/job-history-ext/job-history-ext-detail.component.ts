import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobHistory } from 'app/shared/model/job-history.model';
import {JobHistoryDetailComponent} from "app/entities/job-history/job-history-detail.component";

@Component({
  selector: 'jhi-job-history-detail',
  templateUrl: './job-history-ext-detail.component.html',
})
export class JobHistoryExtDetailComponent extends JobHistoryDetailComponent implements OnInit {

  constructor(protected activatedRoute: ActivatedRoute) {
    super(activatedRoute);
  }

}
