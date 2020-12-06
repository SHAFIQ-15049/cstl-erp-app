import { Component, OnInit, OnDestroy } from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {combineLatest, Subscription} from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILine } from 'app/shared/model/line.model';
import { LineExtService } from './line-ext.service';
import { LineExtDeleteDialogComponent } from './line-ext-delete-dialog.component';
import {LineComponent} from "app/entities/line/line.component";
import {LineService} from "app/entities/line/line.service";
import {ActivatedRoute, Data, ParamMap, Router} from "@angular/router";
import {DepartmentService} from "app/entities/department/department.service";
import {IDepartment} from "app/shared/model/department.model";

@Component({
  selector: 'jhi-line',
  templateUrl: './line-ext.component.html',
})
export class LineExtComponent extends LineComponent implements OnInit, OnDestroy {

  departments: IDepartment[] = [];
  selectedDepartmentId?: number|null|undefined;

  constructor(
    protected lineService: LineExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private departmentService: DepartmentService
  ) {
    super(lineService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if(this.selectedDepartmentId){
      this.lineService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.selectedDepartmentId,
        })
        .subscribe(
          (res: HttpResponse<ILine[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }else{
      this.lineService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<ILine[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }

  }

  private loadDepartments(): void{
    this.departmentService.query({
      size:1000
    }).subscribe((res)=>{
      this.departments = res.body!;
    });
  }


  ngOnInit():void {
    this.loadDepartments();
    super.ngOnInit();
  }

  fetch():void{
    this.loadPage();
  }

  fetchAll(): void{
    this.selectedDepartmentId = null;
    this.fetch();
  }


  ngOnDestroy():void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
      // this.lineService.clearDepartmentId();
    }
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      this.selectedDepartmentId = +params.get('departmentId')!;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  protected onSuccess(data: ILine[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;

    if (navigate) {
      if(this.selectedDepartmentId){
        this.router.navigate(['/line'], {
          queryParams: {
            page: this.page,
            size: this.itemsPerPage,
            sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
            departmentId: this.selectedDepartmentId
          },
        });
      }else{
        this.router.navigate(['/line'], {
          queryParams: {
            page: this.page,
            size: this.itemsPerPage,
            sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
          },
        });
      }

    }else if(this.selectedDepartmentId){
      this.router.navigate(['/line'], {
        queryParams: {
          departmentId: this.selectedDepartmentId
        },
      });
    }
    this.lines = data || [];
    this.ngbPaginationPage = this.page;
  }
}
