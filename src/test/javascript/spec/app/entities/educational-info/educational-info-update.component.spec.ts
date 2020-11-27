import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { EducationalInfoUpdateComponent } from 'app/entities/educational-info/educational-info-update.component';
import { EducationalInfoService } from 'app/entities/educational-info/educational-info.service';
import { EducationalInfo } from 'app/shared/model/educational-info.model';

describe('Component Tests', () => {
  describe('EducationalInfo Management Update Component', () => {
    let comp: EducationalInfoUpdateComponent;
    let fixture: ComponentFixture<EducationalInfoUpdateComponent>;
    let service: EducationalInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [EducationalInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EducationalInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EducationalInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EducationalInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EducationalInfo(123);
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
        const entity = new EducationalInfo();
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
