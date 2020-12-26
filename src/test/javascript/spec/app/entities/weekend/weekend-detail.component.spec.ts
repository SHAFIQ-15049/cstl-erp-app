import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { WeekendDetailComponent } from 'app/entities/weekend/weekend-detail.component';
import { Weekend } from 'app/shared/model/weekend.model';

describe('Component Tests', () => {
  describe('Weekend Management Detail Component', () => {
    let comp: WeekendDetailComponent;
    let fixture: ComponentFixture<WeekendDetailComponent>;
    const route = ({ data: of({ weekend: new Weekend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [WeekendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WeekendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WeekendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load weekend on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.weekend).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
