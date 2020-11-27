import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { EmployeeAccountDetailComponent } from 'app/entities/employee-account/employee-account-detail.component';
import { EmployeeAccount } from 'app/shared/model/employee-account.model';

describe('Component Tests', () => {
  describe('EmployeeAccount Management Detail Component', () => {
    let comp: EmployeeAccountDetailComponent;
    let fixture: ComponentFixture<EmployeeAccountDetailComponent>;
    const route = ({ data: of({ employeeAccount: new EmployeeAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [EmployeeAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employeeAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
