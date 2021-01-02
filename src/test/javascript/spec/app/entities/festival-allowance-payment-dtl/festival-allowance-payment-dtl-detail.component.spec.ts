import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowancePaymentDtlDetailComponent } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl-detail.component';
import { FestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';

describe('Component Tests', () => {
  describe('FestivalAllowancePaymentDtl Management Detail Component', () => {
    let comp: FestivalAllowancePaymentDtlDetailComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentDtlDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ festivalAllowancePaymentDtl: new FestivalAllowancePaymentDtl(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentDtlDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FestivalAllowancePaymentDtlDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FestivalAllowancePaymentDtlDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load festivalAllowancePaymentDtl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.festivalAllowancePaymentDtl).toEqual(jasmine.objectContaining({ id: 123 }));
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
