import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CodeNodeErpTestModule } from '../../../test.module';
import { MonthlySalaryDtlDetailComponent } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl-detail.component';
import { MonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';

describe('Component Tests', () => {
  describe('MonthlySalaryDtl Management Detail Component', () => {
    let comp: MonthlySalaryDtlDetailComponent;
    let fixture: ComponentFixture<MonthlySalaryDtlDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ monthlySalaryDtl: new MonthlySalaryDtl(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [MonthlySalaryDtlDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MonthlySalaryDtlDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MonthlySalaryDtlDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load monthlySalaryDtl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.monthlySalaryDtl).toEqual(jasmine.objectContaining({ id: 123 }));
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
