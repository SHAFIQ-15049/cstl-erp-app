import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { HolidayUpdateComponent } from 'app/entities/holiday/holiday-update.component';
import { HolidayService } from 'app/entities/holiday/holiday.service';
import { Holiday } from 'app/shared/model/holiday.model';

describe('Component Tests', () => {
  describe('Holiday Management Update Component', () => {
    let comp: HolidayUpdateComponent;
    let fixture: ComponentFixture<HolidayUpdateComponent>;
    let service: HolidayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [HolidayUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HolidayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HolidayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HolidayService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Holiday(123);
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
        const entity = new Holiday();
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
