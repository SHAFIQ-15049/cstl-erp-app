import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEducationalInfo } from 'app/shared/model/educational-info.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EducationalInfoExtService } from './educational-info-ext.service';
import { EducationalInfoExtDeleteDialogComponent } from './educational-info-ext-delete-dialog.component';
import {EducationalInfoComponent} from "app/entities/educational-info/educational-info.component";

@Component({
  selector: 'jhi-educational-info',
  templateUrl: './educational-info-ext.component.html',
})
export class EducationalInfoExtComponent extends EducationalInfoComponent implements OnInit, OnDestroy {

  employeeId?: number|null;

  constructor(
    protected educationalInfoService: EducationalInfoExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
  ) {
    super(educationalInfoService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.educationalInfoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IEducationalInfo[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      this.employeeId = this.activatedRoute.snapshot.params['employeeId'];
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

  protected onSuccess(data: IEducationalInfo[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.educationalInfos = data || [];
    this.ngbPaginationPage = this.page;
  }

  addNew():void{
    this.router.navigate(['educational-info/new'], {relativeTo: this.activatedRoute});
  }
}
