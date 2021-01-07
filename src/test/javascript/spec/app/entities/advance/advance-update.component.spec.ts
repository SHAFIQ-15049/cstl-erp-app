import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AdvanceUpdateComponent } from 'app/entities/advance/advance-update.component';
import { AdvanceService } from 'app/entities/advance/advance.service';
import { Advance } from 'app/shared/model/advance.model';

describe('Component Tests', () => {
  describe('Advance Management Update Component', () => {
    let comp: AdvanceUpdateComponent;
    let fixture: ComponentFixture<AdvanceUpdateComponent>;
    let service: AdvanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AdvanceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdvanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Advance(123);
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
        const entity = new Advance();
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
