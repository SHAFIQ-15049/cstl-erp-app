import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import {JhiDataUtils, JhiEventManager} from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITraining } from 'app/shared/model/training.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TrainingExtService } from './training-ext.service';
import { TrainingExtDeleteDialogComponent } from './training-ext-delete-dialog.component';
import {TrainingComponent} from "app/entities/training/training.component";
import {TrainingService} from "app/entities/training/training.service";

@Component({
  selector: 'jhi-training',
  templateUrl: './training-ext.component.html',
})
export class TrainingExtComponent extends TrainingComponent implements OnInit, OnDestroy {

  employeeId?: number | null;

  constructor(
    protected trainingService: TrainingService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(trainingService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    if(this.employeeId){
      this.trainingService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: ['id,asc'],
          'employeeId.equals': this.employeeId,
        })
        .subscribe(
          (res: HttpResponse<ITraining[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
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

  protected onSuccess(data: ITraining[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.trainings = data || [];
    this.ngbPaginationPage = this.page;
  }

}
