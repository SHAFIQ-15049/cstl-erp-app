import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrade } from 'app/shared/model/grade.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GradeService } from './grade.service';
import { GradeDeleteDialogComponent } from './grade-delete-dialog.component';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';

@Component({
  selector: 'jhi-grade',
  templateUrl: './grade.component.html',
})
export class GradeComponent implements OnInit, OnDestroy {
  grades?: IGrade[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  employeeCategory?: EmployeeCategory | null;

  constructor(
    protected gradeService: GradeService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    let queryBuilder = {};
    if (this.employeeCategory) {
      queryBuilder = {
        page: pageToLoad - 1,
        'category.equals': this.employeeCategory,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
    } else {
      queryBuilder = {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
    }
    // eslint-disable-next-line no-console
    console.log(queryBuilder);

    this.gradeService.query(queryBuilder).subscribe(
      (res: HttpResponse<IGrade[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInGrades();
  }

  protected handleNavigation(): void {
    // eslint-disable-next-line no-console
    console.log('Handle navigation called');
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
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

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  addNew(): void {
    this.router.navigate(['grade/new']);
  }

  fetch(): void {
    this.page = 0;
    this.handleNavigation();
  }

  fetchAll(): void {
    this.employeeCategory = null;
    this.fetch();
  }

  trackId(index: number, item: IGrade): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInGrades(): void {
    this.eventSubscriber = this.eventManager.subscribe('gradeListModification', () => this.loadPage());
  }

  delete(grade: IGrade): void {
    const modalRef = this.modalService.open(GradeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.grade = grade;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IGrade[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/grade'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.grades = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
