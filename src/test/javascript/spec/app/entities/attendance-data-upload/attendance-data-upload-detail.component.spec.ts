import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceDataUploadDetailComponent } from 'app/entities/attendance-data-upload/attendance-data-upload-detail.component';
import { AttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

describe('Component Tests', () => {
  describe('AttendanceDataUpload Management Detail Component', () => {
    let comp: AttendanceDataUploadDetailComponent;
    let fixture: ComponentFixture<AttendanceDataUploadDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ attendanceDataUpload: new AttendanceDataUpload(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceDataUploadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttendanceDataUploadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendanceDataUploadDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load attendanceDataUpload on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendanceDataUpload).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
