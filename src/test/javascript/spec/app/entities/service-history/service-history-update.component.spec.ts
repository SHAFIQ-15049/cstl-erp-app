import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { ServiceHistoryUpdateComponent } from 'app/entities/service-history/service-history-update.component';
import { ServiceHistoryService } from 'app/entities/service-history/service-history.service';
import { ServiceHistory } from 'app/shared/model/service-history.model';

describe('Component Tests', () => {
  describe('ServiceHistory Management Update Component', () => {
    let comp: ServiceHistoryUpdateComponent;
    let fixture: ComponentFixture<ServiceHistoryUpdateComponent>;
    let service: ServiceHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [ServiceHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ServiceHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceHistory(123);
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
        const entity = new ServiceHistory();
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
