import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { WeekendUpdateComponent } from 'app/entities/weekend/weekend-update.component';
import { WeekendService } from 'app/entities/weekend/weekend.service';
import { Weekend } from 'app/shared/model/weekend.model';

describe('Component Tests', () => {
  describe('Weekend Management Update Component', () => {
    let comp: WeekendUpdateComponent;
    let fixture: ComponentFixture<WeekendUpdateComponent>;
    let service: WeekendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [WeekendUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WeekendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeekendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeekendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Weekend(123);
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
        const entity = new Weekend();
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
