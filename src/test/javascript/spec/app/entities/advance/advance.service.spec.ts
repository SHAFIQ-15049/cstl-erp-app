import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AdvanceService } from 'app/entities/advance/advance.service';
import { IAdvance, Advance } from 'app/shared/model/advance.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

describe('Service Tests', () => {
  describe('Advance Service', () => {
    let injector: TestBed;
    let service: AdvanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdvance;
    let expectedResult: IAdvance | IAdvance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdvanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Advance(0, currentDate, 'AAAAAAA', 0, 0, 0, PaymentStatus.NOT_PAID, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            providedOn: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Advance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            providedOn: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            providedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new Advance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Advance', () => {
        const returnedFromService = Object.assign(
          {
            providedOn: currentDate.format(DATE_FORMAT),
            reason: 'BBBBBB',
            amount: 1,
            paymentPercentage: 1,
            monthlyPaymentAmount: 1,
            paymentStatus: 'BBBBBB',
            amountPaid: 1,
            amountLeft: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            providedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Advance', () => {
        const returnedFromService = Object.assign(
          {
            providedOn: currentDate.format(DATE_FORMAT),
            reason: 'BBBBBB',
            amount: 1,
            paymentPercentage: 1,
            monthlyPaymentAmount: 1,
            paymentStatus: 'BBBBBB',
            amountPaid: 1,
            amountLeft: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            providedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Advance', () => {
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
