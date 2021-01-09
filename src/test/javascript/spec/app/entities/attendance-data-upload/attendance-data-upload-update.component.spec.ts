import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceDataUploadUpdateComponent } from 'app/entities/attendance-data-upload/attendance-data-upload-update.component';
import { AttendanceDataUploadService } from 'app/entities/attendance-data-upload/attendance-data-upload.service';
import { AttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

describe('Component Tests', () => {
  describe('AttendanceDataUpload Management Update Component', () => {
    let comp: AttendanceDataUploadUpdateComponent;
    let fixture: ComponentFixture<AttendanceDataUploadUpdateComponent>;
    let service: AttendanceDataUploadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceDataUploadUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendanceDataUploadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceDataUploadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceDataUploadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceDataUpload(123);
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
        const entity = new AttendanceDataUpload();
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
