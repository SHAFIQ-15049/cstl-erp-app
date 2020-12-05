import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { LineUpdateComponent } from 'app/entities/line/line-update.component';
import { LineService } from 'app/entities/line/line.service';
import { Line } from 'app/shared/model/line.model';

describe('Component Tests', () => {
  describe('Line Management Update Component', () => {
    let comp: LineUpdateComponent;
    let fixture: ComponentFixture<LineUpdateComponent>;
    let service: LineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [LineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Line(123);
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
        const entity = new Line();
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
