import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { DefaultAllowanceDetailComponent } from 'app/entities/default-allowance/default-allowance-detail.component';
import { DefaultAllowance } from 'app/shared/model/default-allowance.model';

describe('Component Tests', () => {
  describe('DefaultAllowance Management Detail Component', () => {
    let comp: DefaultAllowanceDetailComponent;
    let fixture: ComponentFixture<DefaultAllowanceDetailComponent>;
    const route = ({ data: of({ defaultAllowance: new DefaultAllowance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [DefaultAllowanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DefaultAllowanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DefaultAllowanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load defaultAllowance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.defaultAllowance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
