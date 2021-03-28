import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { IdCardManagementUpdateComponent } from 'app/entities/id-card-management/id-card-management-update.component';
import { IdCardManagementService } from 'app/entities/id-card-management/id-card-management.service';
import { IdCardManagement } from 'app/shared/model/id-card-management.model';

describe('Component Tests', () => {
  describe('IdCardManagement Management Update Component', () => {
    let comp: IdCardManagementUpdateComponent;
    let fixture: ComponentFixture<IdCardManagementUpdateComponent>;
    let service: IdCardManagementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [IdCardManagementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IdCardManagementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdCardManagementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdCardManagementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IdCardManagement(123);
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
        const entity = new IdCardManagement();
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
