import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { LeaveTypeDetailComponent } from 'app/entities/leave-type/leave-type-detail.component';
import { LeaveType } from 'app/shared/model/leave-type.model';

describe('Component Tests', () => {
  describe('LeaveType Management Detail Component', () => {
    let comp: LeaveTypeDetailComponent;
    let fixture: ComponentFixture<LeaveTypeDetailComponent>;
    const route = ({ data: of({ leaveType: new LeaveType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [LeaveTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LeaveTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeaveTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load leaveType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.leaveType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
