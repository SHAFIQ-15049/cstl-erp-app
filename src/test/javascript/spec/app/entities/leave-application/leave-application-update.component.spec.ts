import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { LeaveApplicationUpdateComponent } from 'app/entities/leave-application/leave-application-update.component';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { LeaveApplication } from 'app/shared/model/leave-application.model';

describe('Component Tests', () => {
  describe('LeaveApplication Management Update Component', () => {
    let comp: LeaveApplicationUpdateComponent;
    let fixture: ComponentFixture<LeaveApplicationUpdateComponent>;
    let service: LeaveApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [LeaveApplicationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LeaveApplicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeaveApplicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeaveApplicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LeaveApplication(123);
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
        const entity = new LeaveApplication();
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
