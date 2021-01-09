import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowancePaymentDtlUpdateComponent } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl-update.component';
import { FestivalAllowancePaymentDtlService } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl.service';
import { FestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';

describe('Component Tests', () => {
  describe('FestivalAllowancePaymentDtl Management Update Component', () => {
    let comp: FestivalAllowancePaymentDtlUpdateComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentDtlUpdateComponent>;
    let service: FestivalAllowancePaymentDtlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentDtlUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FestivalAllowancePaymentDtlUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivalAllowancePaymentDtlUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowancePaymentDtlService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FestivalAllowancePaymentDtl(123);
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
        const entity = new FestivalAllowancePaymentDtl();
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
