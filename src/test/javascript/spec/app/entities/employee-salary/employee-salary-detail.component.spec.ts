import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { EmployeeSalaryDetailComponent } from 'app/entities/employee-salary/employee-salary-detail.component';
import { EmployeeSalary } from 'app/shared/model/employee-salary.model';

describe('Component Tests', () => {
  describe('EmployeeSalary Management Detail Component', () => {
    let comp: EmployeeSalaryDetailComponent;
    let fixture: ComponentFixture<EmployeeSalaryDetailComponent>;
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
    });

    describe('OnInit', () => {
      it('Should load employeeSalary on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeSalary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
