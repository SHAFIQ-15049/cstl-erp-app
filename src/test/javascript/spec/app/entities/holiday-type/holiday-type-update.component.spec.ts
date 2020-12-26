import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { HolidayTypeUpdateComponent } from 'app/entities/holiday-type/holiday-type-update.component';
import { HolidayTypeService } from 'app/entities/holiday-type/holiday-type.service';
import { HolidayType } from 'app/shared/model/holiday-type.model';

describe('Component Tests', () => {
  describe('HolidayType Management Update Component', () => {
    let comp: HolidayTypeUpdateComponent;
    let fixture: ComponentFixture<HolidayTypeUpdateComponent>;
    let service: HolidayTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [HolidayTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HolidayTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HolidayTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HolidayTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HolidayType(123);
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
        const entity = new HolidayType();
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
