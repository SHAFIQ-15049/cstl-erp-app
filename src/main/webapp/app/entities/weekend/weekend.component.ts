import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWeekend } from 'app/shared/model/weekend.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { WeekendService } from './weekend.service';
import { WeekendDeleteDialogComponent } from './weekend-delete-dialog.component';

@Component({
  selector: 'jhi-weekend',
  templateUrl: './weekend.component.html',
})
export class WeekendComponent implements OnInit, OnDestroy {
  weekends: IWeekend[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected weekendService: WeekendService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.weekends = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.weekendService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IWeekend[]>) => this.paginateWeekends(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.weekends = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWeekends();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWeekend): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWeekends(): void {
    this.eventSubscriber = this.eventManager.subscribe('weekendListModification', () => this.reset());
  }

  delete(weekend: IWeekend): void {
    const modalRef = this.modalService.open(WeekendDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.weekend = weekend;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateWeekends(data: IWeekend[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.weekends.push(data[i]);
      }
    }
  }
}
