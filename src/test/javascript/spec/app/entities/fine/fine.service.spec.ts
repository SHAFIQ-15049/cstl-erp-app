import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FineService } from 'app/entities/fine/fine.service';
import { IFine, Fine } from 'app/shared/model/fine.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

describe('Service Tests', () => {
  describe('Fine Service', () => {
    let injector: TestBed;
    let service: FineService;
    let httpMock: HttpTestingController;
    let elemDefault: IFine;
    let expectedResult: IFine | IFine[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FineService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Fine(0, currentDate, 'AAAAAAA', 0, 0, 0, PaymentStatus.NOT_PAID, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            finedOn: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Fine', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            finedOn: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            finedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new Fine()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Fine', () => {
        const returnedFromService = Object.assign(
          {
            finedOn: currentDate.format(DATE_FORMAT),
            reason: 'BBBBBB',
            amount: 1,
            finePercentage: 1,
            monthlyFineAmount: 1,
            paymentStatus: 'BBBBBB',
            amountPaid: 1,
            amountLeft: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            finedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Fine', () => {
        const returnedFromService = Object.assign(
          {
            finedOn: currentDate.format(DATE_FORMAT),
            reason: 'BBBBBB',
            amount: 1,
            finePercentage: 1,
            monthlyFineAmount: 1,
            paymentStatus: 'BBBBBB',
            amountPaid: 1,
            amountLeft: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            finedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Fine', () => {
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
