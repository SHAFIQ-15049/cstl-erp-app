import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { ThanaDetailComponent } from 'app/entities/thana/thana-detail.component';
import { Thana } from 'app/shared/model/thana.model';

describe('Component Tests', () => {
  describe('Thana Management Detail Component', () => {
    let comp: ThanaDetailComponent;
    let fixture: ComponentFixture<ThanaDetailComponent>;
    const route = ({ data: of({ thana: new Thana(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [ThanaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ThanaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThanaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load thana on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.thana).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
