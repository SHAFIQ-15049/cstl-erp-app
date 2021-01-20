import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FestivalAllowancePaymentService } from 'app/entities/festival-allowance-payment/festival-allowance-payment.service';
import { IFestivalAllowancePayment, FestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

describe('Service Tests', () => {
  describe('FestivalAllowancePayment Service', () => {
    let injector: TestBed;
    let service: FestivalAllowancePaymentService;
    let httpMock: HttpTestingController;
    let elemDefault: IFestivalAllowancePayment;
    let expectedResult: IFestivalAllowancePayment | IFestivalAllowancePayment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FestivalAllowancePaymentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FestivalAllowancePayment(0, 0, MonthType.JANUARY, SalaryExecutionStatus.DONE, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            executedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FestivalAllowancePayment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            executedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new FestivalAllowancePayment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FestivalAllowancePayment', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FestivalAllowancePayment', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FestivalAllowancePayment', () => {
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
