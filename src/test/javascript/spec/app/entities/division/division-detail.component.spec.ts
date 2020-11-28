import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { DivisionDetailComponent } from 'app/entities/division/division-detail.component';
import { Division } from 'app/shared/model/division.model';

describe('Component Tests', () => {
  describe('Division Management Detail Component', () => {
    let comp: DivisionDetailComponent;
    let fixture: ComponentFixture<DivisionDetailComponent>;
    const route = ({ data: of({ division: new Division(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [DivisionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DivisionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DivisionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load division on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.division).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
