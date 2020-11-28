import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { DistrictUpdateComponent } from 'app/entities/district/district-update.component';
import { DistrictService } from 'app/entities/district/district.service';
import { District } from 'app/shared/model/district.model';

describe('Component Tests', () => {
  describe('District Management Update Component', () => {
    let comp: DistrictUpdateComponent;
    let fixture: ComponentFixture<DistrictUpdateComponent>;
    let service: DistrictService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [DistrictUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DistrictUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DistrictUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DistrictService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new District(123);
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
        const entity = new District();
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
