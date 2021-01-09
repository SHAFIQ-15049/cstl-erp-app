import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { LeaveApplicationDetailComponent } from 'app/entities/leave-application/leave-application-detail.component';
import { LeaveApplication } from 'app/shared/model/leave-application.model';

describe('Component Tests', () => {
  describe('LeaveApplication Management Detail Component', () => {
    let comp: LeaveApplicationDetailComponent;
    let fixture: ComponentFixture<LeaveApplicationDetailComponent>;
    const route = ({ data: of({ leaveApplication: new LeaveApplication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [LeaveApplicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LeaveApplicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeaveApplicationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load leaveApplication on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.leaveApplication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
