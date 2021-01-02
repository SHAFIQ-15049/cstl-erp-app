import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowancePaymentUpdateComponent } from 'app/entities/festival-allowance-payment/festival-allowance-payment-update.component';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';
import { FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';

describe('Component Tests', () => {
  describe('FestivalAllowancePayment Management Update Component', () => {
    let comp: FestivalAllowancePaymentUpdateComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentUpdateComponent>;
    let service: FestivalAllowancePaymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FestivalAllowancePaymentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivalAllowancePaymentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowancePaymentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FestivalAllowancePayment(123);
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
        const entity = new FestivalAllowancePayment();
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
