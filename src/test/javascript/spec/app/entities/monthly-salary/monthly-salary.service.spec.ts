import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MonthlySalaryService } from 'app/entities/monthly-salary/monthly-salary.service';
import { IMonthlySalary, MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

describe('Service Tests', () => {
  describe('MonthlySalary Service', () => {
    let injector: TestBed;
    let service: MonthlySalaryService;
    let httpMock: HttpTestingController;
    let elemDefault: IMonthlySalary;
    let expectedResult: IMonthlySalary | IMonthlySalary[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MonthlySalaryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MonthlySalary(
        0,
        0,
        MonthType.JANUARY,
        currentDate,
        currentDate,
        SalaryExecutionStatus.DONE,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            executedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MonthlySalary', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            executedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            executedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new MonthlySalary()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MonthlySalary', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            executedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MonthlySalary', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
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

      it('should delete a MonthlySalary', () => {
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
