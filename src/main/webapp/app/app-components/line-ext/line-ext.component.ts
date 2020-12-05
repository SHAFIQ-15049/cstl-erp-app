import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILine } from 'app/shared/model/line.model';
import { LineExtService } from './line-ext.service';
import { LineExtDeleteDialogComponent } from './line-ext-delete-dialog.component';
import {LineComponent} from "app/entities/line/line.component";
import {LineService} from "app/entities/line/line.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DepartmentService} from "app/entities/department/department.service";
import {IDepartment} from "app/shared/model/department.model";

@Component({
  selector: 'jhi-line',
  templateUrl: './line-ext.component.html',
})
export class LineExtComponent extends LineComponent implements OnInit, OnDestroy {

  departments: IDepartment[] = [];
  selectedDepartmentId?: number|null;

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

    if(this.lineService.getDepartmentId()){
      this.lineService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.lineService.getDepartmentId(),
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
    this.selectedDepartmentId = this.lineService.getDepartmentId();
    this.loadDepartments();
    super.ngOnInit();
  }

  fetch():void{
    this.lineService.setDepartmentId(this.selectedDepartmentId!);
    this.handleNavigation();
  }

  fetchAll(): void{
    this.lineService.clearDepartmentId();
    this.selectedDepartmentId = null;
    this.handleNavigation();
  }


  ngOnDestroy():void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
      // this.lineService.clearDepartmentId();
    }
  }
}
