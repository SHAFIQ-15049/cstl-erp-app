import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { DefaultAllowanceUpdateComponent } from 'app/entities/default-allowance/default-allowance-update.component';
import { DefaultAllowanceService } from 'app/entities/default-allowance/default-allowance.service';
import { DefaultAllowance } from 'app/shared/model/default-allowance.model';

describe('Component Tests', () => {
  describe('DefaultAllowance Management Update Component', () => {
    let comp: DefaultAllowanceUpdateComponent;
    let fixture: ComponentFixture<DefaultAllowanceUpdateComponent>;
    let service: DefaultAllowanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [DefaultAllowanceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DefaultAllowanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DefaultAllowanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DefaultAllowanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DefaultAllowance(123);
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
        const entity = new DefaultAllowance();
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
