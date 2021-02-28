import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MonthlySalaryDtlService } from 'app/entities/monthly-salary-dtl/monthly-salary-dtl.service';
import { IMonthlySalaryDtl, MonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';
import { PayrollGenerationType } from 'app/shared/model/enumerations/payroll-generation-type.model';

describe('Service Tests', () => {
  describe('MonthlySalaryDtl Service', () => {
    let injector: TestBed;
    let service: MonthlySalaryDtlService;
    let httpMock: HttpTestingController;
    let elemDefault: IMonthlySalaryDtl;
    let expectedResult: IMonthlySalaryDtl | IMonthlySalaryDtl[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MonthlySalaryDtlService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MonthlySalaryDtl(
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
        0,
        0,
        0,
        0,
        0,
        0,
        SalaryExecutionStatus.DONE,
        PayrollGenerationType.FULL,
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a MonthlySalaryDtl', () => {
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

        service.create(new MonthlySalaryDtl()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MonthlySalaryDtl', () => {
        const returnedFromService = Object.assign(
          {
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
            totalWorkingDays: 1,
            regularLeave: 1,
            sickLeave: 1,
            compensationLeave: 1,
            festivalLeave: 1,
            weeklyLeave: 1,
            present: 1,
            absent: 1,
            totalMonthDays: 1,
            overTimeHour: 1,
            overTimeSalaryHourly: 1,
            overTimeSalary: 1,
            presentBonus: 1,
            absentFine: 1,
            stampPrice: 1,
            tax: 1,
            others: 1,
            totalPayable: 1,
            status: 'BBBBBB',
            type: 'BBBBBB',
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

      it('should return a list of MonthlySalaryDtl', () => {
        const returnedFromService = Object.assign(
          {
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
            totalWorkingDays: 1,
            regularLeave: 1,
            sickLeave: 1,
            compensationLeave: 1,
            festivalLeave: 1,
            weeklyLeave: 1,
            present: 1,
            absent: 1,
            totalMonthDays: 1,
            overTimeHour: 1,
            overTimeSalaryHourly: 1,
            overTimeSalary: 1,
            presentBonus: 1,
            absentFine: 1,
            stampPrice: 1,
            tax: 1,
            others: 1,
            totalPayable: 1,
            status: 'BBBBBB',
            type: 'BBBBBB',
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

      it('should delete a MonthlySalaryDtl', () => {
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
