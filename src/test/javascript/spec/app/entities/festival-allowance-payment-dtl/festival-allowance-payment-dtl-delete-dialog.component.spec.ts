import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CodeNodeErpTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { FestivalAllowancePaymentDtlDeleteDialogComponent } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl-delete-dialog.component';
import { FestivalAllowancePaymentDtlService } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl.service';

describe('Component Tests', () => {
  describe('FestivalAllowancePaymentDtl Management Delete Component', () => {
    let comp: FestivalAllowancePaymentDtlDeleteDialogComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentDtlDeleteDialogComponent>;
    let service: FestivalAllowancePaymentDtlService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentDtlDeleteDialogComponent],
      })
        .overrideTemplate(FestivalAllowancePaymentDtlDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FestivalAllowancePaymentDtlDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowancePaymentDtlService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
