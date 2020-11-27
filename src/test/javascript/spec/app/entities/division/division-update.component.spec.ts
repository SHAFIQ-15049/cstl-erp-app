import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { DivisionUpdateComponent } from 'app/entities/division/division-update.component';
import { DivisionService } from 'app/entities/division/division.service';
import { Division } from 'app/shared/model/division.model';

describe('Component Tests', () => {
  describe('Division Management Update Component', () => {
    let comp: DivisionUpdateComponent;
    let fixture: ComponentFixture<DivisionUpdateComponent>;
    let service: DivisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [DivisionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DivisionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DivisionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DivisionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Division(123);
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
        const entity = new Division();
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
