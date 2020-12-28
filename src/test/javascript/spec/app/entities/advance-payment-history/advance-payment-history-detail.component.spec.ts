import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AdvancePaymentHistoryDetailComponent } from 'app/entities/advance-payment-history/advance-payment-history-detail.component';
import { AdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';

describe('Component Tests', () => {
  describe('AdvancePaymentHistory Management Detail Component', () => {
    let comp: AdvancePaymentHistoryDetailComponent;
    let fixture: ComponentFixture<AdvancePaymentHistoryDetailComponent>;
    const route = ({ data: of({ advancePaymentHistory: new AdvancePaymentHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AdvancePaymentHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AdvancePaymentHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdvancePaymentHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load advancePaymentHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.advancePaymentHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
