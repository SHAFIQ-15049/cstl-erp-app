import { Component, OnInit } from '@angular/core';
import { FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { FestivalAllowancePaymentComponent } from 'app/entities/festival-allowance-payment/festival-allowance-payment.component';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-festival-allowance-employee',
  templateUrl: './festival-allowance-employee.component.html',
  styleUrls: ['./festival-allowance-employee.component.scss'],
})
export class FestivalAllowanceEmployeeComponent extends FestivalAllowancePaymentComponent implements OnInit {
  constructor(
    protected festivalAllowancePaymentService: FestivalAllowancePaymentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected jhiAlertService: JhiAlertService,
    protected designationService: DesignationService
  ) {
    super(festivalAllowancePaymentService, activatedRoute, router, eventManager, modalService, jhiAlertService, designationService);
  }

  ngOnInit(): void {}
}
