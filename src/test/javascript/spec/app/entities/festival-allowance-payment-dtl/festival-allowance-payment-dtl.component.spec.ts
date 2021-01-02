import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowancePaymentDtlComponent } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl.component';
import { FestivalAllowancePaymentDtlService } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl.service';
import { FestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';

describe('Component Tests', () => {
  describe('FestivalAllowancePaymentDtl Management Component', () => {
    let comp: FestivalAllowancePaymentDtlComponent;
    let fixture: ComponentFixture<FestivalAllowancePaymentDtlComponent>;
    let service: FestivalAllowancePaymentDtlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowancePaymentDtlComponent],
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
        .overrideTemplate(FestivalAllowancePaymentDtlComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivalAllowancePaymentDtlComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowancePaymentDtlService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FestivalAllowancePaymentDtl(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.festivalAllowancePaymentDtls && comp.festivalAllowancePaymentDtls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FestivalAllowancePaymentDtl(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.festivalAllowancePaymentDtls && comp.festivalAllowancePaymentDtls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
