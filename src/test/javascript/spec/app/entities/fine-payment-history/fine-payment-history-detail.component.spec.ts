import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FinePaymentHistoryDetailComponent } from 'app/entities/fine-payment-history/fine-payment-history-detail.component';
import { FinePaymentHistory } from 'app/shared/model/fine-payment-history.model';

describe('Component Tests', () => {
  describe('FinePaymentHistory Management Detail Component', () => {
    let comp: FinePaymentHistoryDetailComponent;
    let fixture: ComponentFixture<FinePaymentHistoryDetailComponent>;
    const route = ({ data: of({ finePaymentHistory: new FinePaymentHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FinePaymentHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FinePaymentHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FinePaymentHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load finePaymentHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.finePaymentHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
