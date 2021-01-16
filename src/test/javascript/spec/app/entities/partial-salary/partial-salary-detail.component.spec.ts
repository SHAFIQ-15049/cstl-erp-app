import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CodeNodeErpTestModule } from '../../../test.module';
import { PartialSalaryDetailComponent } from 'app/entities/partial-salary/partial-salary-detail.component';
import { PartialSalary } from 'app/shared/model/partial-salary.model';

describe('Component Tests', () => {
  describe('PartialSalary Management Detail Component', () => {
    let comp: PartialSalaryDetailComponent;
    let fixture: ComponentFixture<PartialSalaryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ partialSalary: new PartialSalary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [PartialSalaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartialSalaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartialSalaryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load partialSalary on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partialSalary).toEqual(jasmine.objectContaining({ id: 123 }));
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
