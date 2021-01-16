import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PartialSalaryService } from 'app/entities/partial-salary/partial-salary.service';
import { IPartialSalary, PartialSalary } from 'app/shared/model/partial-salary.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

describe('Service Tests', () => {
  describe('PartialSalary Service', () => {
    let injector: TestBed;
    let service: PartialSalaryService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartialSalary;
    let expectedResult: IPartialSalary | IPartialSalary[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartialSalaryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PartialSalary(
        0,
        0,
        MonthType.JANUARY,
        0,
        currentDate,
        currentDate,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        SalaryExecutionStatus.DONE,
        currentDate,
        'AAAAAAA',
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

      it('should create a PartialSalary', () => {
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

        service.create(new PartialSalary()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PartialSalary', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
            totalMonthDays: 1,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            gross: 1,
            basic: 1,
            basicPercent: 1,
            houseRent: 1,
            houseRentPercent: 1,
            medicalAllowance: 1,
            medicalAllowancePercent: 1,
            convinceAllowance: 1,
            convinceAllowancePercent: 1,
            foodAllowance: 1,
            foodAllowancePercent: 1,
            fine: 1,
            advance: 1,
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
            note: 'BBBBBB',
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

      it('should return a list of PartialSalary', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
            totalMonthDays: 1,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            toDate: currentDate.format(DATE_TIME_FORMAT),
            gross: 1,
            basic: 1,
            basicPercent: 1,
            houseRent: 1,
            houseRentPercent: 1,
            medicalAllowance: 1,
            medicalAllowancePercent: 1,
            convinceAllowance: 1,
            convinceAllowancePercent: 1,
            foodAllowance: 1,
            foodAllowancePercent: 1,
            fine: 1,
            advance: 1,
            status: 'BBBBBB',
            executedOn: currentDate.format(DATE_TIME_FORMAT),
            executedBy: 'BBBBBB',
            note: 'BBBBBB',
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

      it('should delete a PartialSalary', () => {
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
