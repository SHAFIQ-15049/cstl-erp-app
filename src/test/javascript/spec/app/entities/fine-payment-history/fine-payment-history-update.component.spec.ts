import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FinePaymentHistoryUpdateComponent } from 'app/entities/fine-payment-history/fine-payment-history-update.component';
import { FinePaymentHistoryService } from 'app/entities/fine-payment-history/fine-payment-history.service';
import { FinePaymentHistory } from 'app/shared/model/fine-payment-history.model';

describe('Component Tests', () => {
  describe('FinePaymentHistory Management Update Component', () => {
    let comp: FinePaymentHistoryUpdateComponent;
    let fixture: ComponentFixture<FinePaymentHistoryUpdateComponent>;
    let service: FinePaymentHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FinePaymentHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FinePaymentHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FinePaymentHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinePaymentHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FinePaymentHistory(123);
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
        const entity = new FinePaymentHistory();
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
