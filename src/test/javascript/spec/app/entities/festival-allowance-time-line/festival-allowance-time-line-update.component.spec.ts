import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowanceTimeLineUpdateComponent } from 'app/entities/festival-allowance-time-line/festival-allowance-time-line-update.component';
import { FestivalAllowanceTimeLineService } from 'app/entities/festival-allowance-time-line/festival-allowance-time-line.service';
import { FestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';

describe('Component Tests', () => {
  describe('FestivalAllowanceTimeLine Management Update Component', () => {
    let comp: FestivalAllowanceTimeLineUpdateComponent;
    let fixture: ComponentFixture<FestivalAllowanceTimeLineUpdateComponent>;
    let service: FestivalAllowanceTimeLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowanceTimeLineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FestivalAllowanceTimeLineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivalAllowanceTimeLineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowanceTimeLineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FestivalAllowanceTimeLine(123);
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
        const entity = new FestivalAllowanceTimeLine();
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
