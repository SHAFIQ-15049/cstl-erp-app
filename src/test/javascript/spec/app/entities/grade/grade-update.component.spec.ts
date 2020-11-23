import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { GradeUpdateComponent } from 'app/entities/grade/grade-update.component';
import { GradeService } from 'app/entities/grade/grade.service';
import { Grade } from 'app/shared/model/grade.model';

describe('Component Tests', () => {
  describe('Grade Management Update Component', () => {
    let comp: GradeUpdateComponent;
    let fixture: ComponentFixture<GradeUpdateComponent>;
    let service: GradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [GradeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GradeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Grade(123);
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
        const entity = new Grade();
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
