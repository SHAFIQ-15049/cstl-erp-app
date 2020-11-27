import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { ThanaUpdateComponent } from 'app/entities/thana/thana-update.component';
import { ThanaService } from 'app/entities/thana/thana.service';
import { Thana } from 'app/shared/model/thana.model';

describe('Component Tests', () => {
  describe('Thana Management Update Component', () => {
    let comp: ThanaUpdateComponent;
    let fixture: ComponentFixture<ThanaUpdateComponent>;
    let service: ThanaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [ThanaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ThanaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThanaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThanaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Thana(123);
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
        const entity = new Thana();
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
