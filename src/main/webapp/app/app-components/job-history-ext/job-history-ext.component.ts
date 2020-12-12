import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobHistory } from 'app/shared/model/job-history.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JobHistoryExtService } from './job-history-ext.service';
import { JobHistoryExtDeleteDialogComponent } from './job-history-ext-delete-dialog.component';
import {JobHistoryComponent} from "app/entities/job-history/job-history.component";
import {IEmployee} from "app/shared/model/employee.model";

@Component({
  selector: 'jhi-job-history',
  templateUrl: './job-history-ext.component.html',
})
export class JobHistoryExtComponent extends JobHistoryComponent implements OnInit, OnDestroy {

  employeeId?: number | null;

  constructor(
    protected jobHistoryService: JobHistoryExtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(jobHistoryService, activatedRoute, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if(this.employeeId){
      this.jobHistoryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          'employeeId.equals': this.employeeId,
          sort: ['serial,asc'],
        })
        .subscribe(
          (res: HttpResponse<IJobHistory[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }else{
      this.jobHistoryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IJobHistory[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }

  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      this.employeeId = +params.get("employeeId")!;
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  protected onSuccess(data: IJobHistory[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.jobHistories = data || [];
    this.ngbPaginationPage = this.page;
  }

}
