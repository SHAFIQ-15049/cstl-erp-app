import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { MonthlySalaryDtlUpdateComponent } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl-update.component';
import { MonthlySalaryDtlService } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl.service';
import { MonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';

describe('Component Tests', () => {
  describe('MonthlySalaryDtl Management Update Component', () => {
    let comp: MonthlySalaryDtlUpdateComponent;
    let fixture: ComponentFixture<MonthlySalaryDtlUpdateComponent>;
    let service: MonthlySalaryDtlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [MonthlySalaryDtlUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MonthlySalaryDtlUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonthlySalaryDtlUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MonthlySalaryDtlService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MonthlySalaryDtl(123);
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
        const entity = new MonthlySalaryDtl();
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
