import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { PartialSalaryUpdateComponent } from 'app/entities/partial-salary/partial-salary-update.component';
import { PartialSalaryService } from 'app/entities/partial-salary/partial-salary.service';
import { PartialSalary } from 'app/shared/model/partial-salary.model';

describe('Component Tests', () => {
  describe('PartialSalary Management Update Component', () => {
    let comp: PartialSalaryUpdateComponent;
    let fixture: ComponentFixture<PartialSalaryUpdateComponent>;
    let service: PartialSalaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [PartialSalaryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartialSalaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartialSalaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartialSalaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartialSalary(123);
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
        const entity = new PartialSalary();
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
