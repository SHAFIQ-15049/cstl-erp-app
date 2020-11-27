import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { EmployeeAccountUpdateComponent } from 'app/entities/employee-account/employee-account-update.component';
import { EmployeeAccountService } from 'app/entities/employee-account/employee-account.service';
import { EmployeeAccount } from 'app/shared/model/employee-account.model';

describe('Component Tests', () => {
  describe('EmployeeAccount Management Update Component', () => {
    let comp: EmployeeAccountUpdateComponent;
    let fixture: ComponentFixture<EmployeeAccountUpdateComponent>;
    let service: EmployeeAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [EmployeeAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeeAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeAccount(123);
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
        const entity = new EmployeeAccount();
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
