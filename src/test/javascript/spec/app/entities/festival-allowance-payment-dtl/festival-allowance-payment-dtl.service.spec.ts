import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FestivalAllowancePaymentDtlService } from 'app/entities/festival-allowance-payment-dtl/festival-allowance-payment-dtl.service';
import { IFestivalAllowancePaymentDtl, FestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

describe('Service Tests', () => {
  describe('FestivalAllowancePaymentDtl Service', () => {
    let injector: TestBed;
    let service: FestivalAllowancePaymentDtlService;
    let httpMock: HttpTestingController;
    let elemDefault: IFestivalAllowancePaymentDtl;
    let expectedResult: IFestivalAllowancePaymentDtl | IFestivalAllowancePaymentDtl[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FestivalAllowancePaymentDtlService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FestivalAllowancePaymentDtl(0, 0, SalaryExecutionStatus.DONE, currentDate, 'AAAAAAA', 'AAAAAAA');
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

      it('should create a FestivalAllowancePaymentDtl', () => {
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

        service.create(new FestivalAllowancePaymentDtl()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FestivalAllowancePaymentDtl', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
            note: 'BBBBBB',
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

      it('should return a list of FestivalAllowancePaymentDtl', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
            note: 'BBBBBB',
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

      it('should delete a FestivalAllowancePaymentDtl', () => {
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
