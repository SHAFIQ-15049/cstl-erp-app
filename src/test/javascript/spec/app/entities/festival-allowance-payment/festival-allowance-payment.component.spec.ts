import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowancePaymentComponent } from 'app/entities/festival-allowance-payment/festival-allowance-payment.component';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';
import { FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';

describe('Component Tests', () => {
  describe('FestivalAllowancePayment Management Component', () => {
    let comp: FestivalAllowancePaymentComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentComponent>;
    let service: FestivalAllowancePaymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(FestivalAllowancePaymentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivalAllowancePaymentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowancePaymentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FestivalAllowancePayment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.festivalAllowancePayments && comp.festivalAllowancePayments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FestivalAllowancePayment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.festivalAllowancePayments && comp.festivalAllowancePayments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
