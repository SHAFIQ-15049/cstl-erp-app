import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { MonthlySalaryUpdateComponent } from 'app/entities/monthly-salary/monthly-salary-update.component';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { MonthlySalary } from 'app/shared/model/monthly-salary.model';

describe('Component Tests', () => {
  describe('MonthlySalary Management Update Component', () => {
    let comp: MonthlySalaryUpdateComponent;
    let fixture: ComponentFixture<MonthlySalaryUpdateComponent>;
    let service: MonthlySalaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [MonthlySalaryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MonthlySalaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonthlySalaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MonthlySalaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MonthlySalary(123);
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
        const entity = new MonthlySalary();
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
