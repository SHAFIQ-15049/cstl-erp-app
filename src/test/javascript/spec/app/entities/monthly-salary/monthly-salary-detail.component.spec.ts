import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { MonthlySalaryDetailComponent } from 'app/entities/monthly-salary/monthly-salary-detail.component';
import { MonthlySalary } from 'app/shared/model/monthly-salary.model';

describe('Component Tests', () => {
  describe('MonthlySalary Management Detail Component', () => {
    let comp: MonthlySalaryDetailComponent;
    let fixture: ComponentFixture<MonthlySalaryDetailComponent>;
    const route = ({ data: of({ monthlySalary: new MonthlySalary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [MonthlySalaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MonthlySalaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MonthlySalaryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load monthlySalary on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.monthlySalary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
