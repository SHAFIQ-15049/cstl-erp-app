import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AdvancePaymentHistoryUpdateComponent } from 'app/entities/advance-payment-history/advance-payment-history-update.component';
import { AdvancePaymentHistoryService } from 'app/entities/advance-payment-history/advance-payment-history.service';
import { AdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';

describe('Component Tests', () => {
  describe('AdvancePaymentHistory Management Update Component', () => {
    let comp: AdvancePaymentHistoryUpdateComponent;
    let fixture: ComponentFixture<AdvancePaymentHistoryUpdateComponent>;
    let service: AdvancePaymentHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AdvancePaymentHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdvancePaymentHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvancePaymentHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvancePaymentHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdvancePaymentHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdvancePaymentHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
