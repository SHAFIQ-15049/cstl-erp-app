import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowanceTimeLineDetailComponent } from 'app/entities/festival-allowance-time-line/festival-allowance-time-line-detail.component';
import { FestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';

describe('Component Tests', () => {
  describe('FestivalAllowanceTimeLine Management Detail Component', () => {
    let comp: FestivalAllowanceTimeLineDetailComponent;
    let fixture: ComponentFixture<FestivalAllowanceTimeLineDetailComponent>;
    const route = ({ data: of({ festivalAllowanceTimeLine: new FestivalAllowanceTimeLine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowanceTimeLineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FestivalAllowanceTimeLineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FestivalAllowanceTimeLineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load festivalAllowanceTimeLine on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.festivalAllowanceTimeLine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
