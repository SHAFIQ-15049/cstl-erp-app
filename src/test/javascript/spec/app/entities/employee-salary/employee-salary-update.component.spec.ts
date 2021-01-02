import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { EmployeeSalaryUpdateComponent } from 'app/entities/employee-salary/employee-salary-update.component';
import { EmployeeSalaryService } from 'app/entities/employee-salary/employee-salary.service';
import { EmployeeSalary } from 'app/shared/model/employee-salary.model';

describe('Component Tests', () => {
  describe('EmployeeSalary Management Update Component', () => {
    let comp: EmployeeSalaryUpdateComponent;
    let fixture: ComponentFixture<EmployeeSalaryUpdateComponent>;
    let service: EmployeeSalaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [EmployeeSalaryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeeSalaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeSalaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeSalaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeSalary(123);
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
        const entity = new EmployeeSalary();
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
