import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILine } from 'app/shared/model/line.model';
import { LineService } from './line.service';
import { LineDeleteDialogComponent } from './line-delete-dialog.component';

@Component({
  selector: 'jhi-line',
  templateUrl: './line.component.html',
})
export class LineComponent implements OnInit, OnDestroy {
  lines?: ILine[];
  eventSubscriber?: Subscription;

  constructor(
    protected lineService: LineService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.lineService.query().subscribe((res: HttpResponse<ILine[]>) => (this.lines = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLines();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILine): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInLines(): void {
    this.eventSubscriber = this.eventManager.subscribe('lineListModification', () => this.loadAll());
  }

  delete(line: ILine): void {
    const modalRef = this.modalService.open(LineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.line = line;
  }
}
