import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceUpdateComponent } from 'app/entities/attendance/attendance-update.component';
import { AttendanceService } from 'app/entities/attendance/attendance.service';
import { Attendance } from 'app/shared/model/attendance.model';

describe('Component Tests', () => {
  describe('Attendance Management Update Component', () => {
    let comp: AttendanceUpdateComponent;
    let fixture: ComponentFixture<AttendanceUpdateComponent>;
    let service: AttendanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Attendance(123);
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
        const entity = new Attendance();
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
