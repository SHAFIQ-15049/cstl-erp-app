import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { DesignationUpdateComponent } from 'app/entities/designation/designation-update.component';
import { DesignationService } from 'app/entities/designation/designation.service';
import { Designation } from 'app/shared/model/designation.model';

describe('Component Tests', () => {
  describe('Designation Management Update Component', () => {
    let comp: DesignationUpdateComponent;
    let fixture: ComponentFixture<DesignationUpdateComponent>;
    let service: DesignationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [DesignationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DesignationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DesignationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DesignationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Designation(123);
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
        const entity = new Designation();
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
