import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdvancePaymentHistoryService } from 'app/entities/advance-payment-history/advance-payment-history.service';
import { IAdvancePaymentHistory, AdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

describe('Service Tests', () => {
  describe('AdvancePaymentHistory Service', () => {
    let injector: TestBed;
    let service: AdvancePaymentHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdvancePaymentHistory;
    let expectedResult: IAdvancePaymentHistory | IAdvancePaymentHistory[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdvancePaymentHistoryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AdvancePaymentHistory(0, 0, MonthType.JANUARY, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AdvancePaymentHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AdvancePaymentHistory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AdvancePaymentHistory', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            monthType: 'BBBBBB',
            amount: 1,
            before: 1,
            after: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AdvancePaymentHistory', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            monthType: 'BBBBBB',
            amount: 1,
            before: 1,
            after: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AdvancePaymentHistory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
