import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CodeNodeErpTestModule } from '../../../test.module';
import { EmployeeSalaryDetailComponent } from 'app/entities/employee-salary/employee-salary-detail.component';
import { EmployeeSalary } from 'app/shared/model/employee-salary.model';

describe('Component Tests', () => {
  describe('EmployeeSalary Management Detail Component', () => {
    let comp: EmployeeSalaryDetailComponent;
    let fixture: ComponentFixture<EmployeeSalaryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ employeeSalary: new EmployeeSalary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [EmployeeSalaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeSalaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeSalaryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load employeeSalary on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeSalary).toEqual(jasmine.objectContaining({ id: 123 }));
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
