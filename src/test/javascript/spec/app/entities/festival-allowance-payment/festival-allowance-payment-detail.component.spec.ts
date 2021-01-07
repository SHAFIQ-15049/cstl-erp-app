import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowancePaymentDetailComponent } from 'app/entities/festival-allowance-payment/festival-allowance-payment-detail.component';
import { FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';

describe('Component Tests', () => {
  describe('FestivalAllowancePayment Management Detail Component', () => {
    let comp: FestivalAllowancePaymentDetailComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentDetailComponent>;
    const route = ({ data: of({ festivalAllowancePayment: new FestivalAllowancePayment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FestivalAllowancePaymentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FestivalAllowancePaymentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load festivalAllowancePayment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.festivalAllowancePayment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
