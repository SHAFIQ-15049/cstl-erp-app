import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import {JhiDataUtils, JhiEventManager} from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceHistory } from 'app/shared/model/service-history.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ServiceHistoryExtService } from './service-history-ext.service';
import { ServiceHistoryExtDeleteDialogComponent } from './service-history-ext-delete-dialog.component';
import {ServiceHistoryComponent} from "app/entities/service-history/service-history.component";
import {ServiceHistoryService} from "app/entities/service-history/service-history.service";

@Component({
  selector: 'jhi-service-history',
  templateUrl: './service-history-ext.component.html',
})
export class ServiceHistoryExtComponent extends ServiceHistoryComponent implements OnInit, OnDestroy {

  employeeId?: number | null;

  constructor(
    protected serviceHistoryService: ServiceHistoryService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(serviceHistoryService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if(this.employeeId){
      this.serviceHistoryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          'employeeId.equals': this.employeeId,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IServiceHistory[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }else{
      this.serviceHistoryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IServiceHistory[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }

  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      this.employeeId = +params.get('employeeId')!;
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  protected onSuccess(data: IServiceHistory[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.serviceHistories = data || [];
    this.ngbPaginationPage = this.page;
  }

}
