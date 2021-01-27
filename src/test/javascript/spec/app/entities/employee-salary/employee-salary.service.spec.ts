import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeSalaryService } from 'app/entities/employee-salary/employee-salary.service';
import { IEmployeeSalary, EmployeeSalary } from 'app/shared/model/employee-salary.model';
import { ActiveStatus } from 'app/shared/model/enumerations/active-status.model';
import { InsuranceProcessType } from 'app/shared/model/enumerations/insurance-process-type.model';

describe('Service Tests', () => {
  describe('EmployeeSalary Service', () => {
    let injector: TestBed;
    let service: EmployeeSalaryService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeeSalary;
    let expectedResult: IEmployeeSalary | IEmployeeSalary[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeSalaryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EmployeeSalary(
        0,
        0,
        0,
        0,
        currentDate,
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
        ActiveStatus.ACTIVE,
        0,
        0,
        'AAAAAAA',
        ActiveStatus.ACTIVE,
        0,
        0,
        'AAAAAAA',
        InsuranceProcessType.PROCESS_WITH_SALARY,
        ActiveStatus.ACTIVE
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            salaryStartDate: currentDate.format(DATE_TIME_FORMAT),
            salaryEndDate: currentDate.format(DATE_TIME_FORMAT),
            nextIncrementDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmployeeSalary', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            salaryStartDate: currentDate.format(DATE_TIME_FORMAT),
            salaryEndDate: currentDate.format(DATE_TIME_FORMAT),
            nextIncrementDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            salaryStartDate: currentDate,
            salaryEndDate: currentDate,
            nextIncrementDate: currentDate,
          },
          returnedFromService
        );

        service.create(new EmployeeSalary()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeeSalary', () => {
        const returnedFromService = Object.assign(
          {
            gross: 1,
            incrementAmount: 1,
            incrementPercentage: 1,
            salaryStartDate: currentDate.format(DATE_TIME_FORMAT),
            salaryEndDate: currentDate.format(DATE_TIME_FORMAT),
            nextIncrementDate: currentDate.format(DATE_TIME_FORMAT),
            basic: 1,
            basicPercent: 1,
            houseRent: 1,
            houseRentPercent: 1,
            totalAllowance: 1,
            medicalAllowance: 1,
            medicalAllowancePercent: 1,
            convinceAllowance: 1,
            convinceAllowancePercent: 1,
            foodAllowance: 1,
            foodAllowancePercent: 1,
            specialAllowanceActiveStatus: 'BBBBBB',
            specialAllowance: 1,
            specialAllowancePercent: 1,
            specialAllowanceDescription: 'BBBBBB',
            insuranceActiveStatus: 'BBBBBB',
            insuranceAmount: 1,
            insurancePercent: 1,
            insuranceDescription: 'BBBBBB',
            insuranceProcessType: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            salaryStartDate: currentDate,
            salaryEndDate: currentDate,
            nextIncrementDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeeSalary', () => {
        const returnedFromService = Object.assign(
          {
            gross: 1,
            incrementAmount: 1,
            incrementPercentage: 1,
            salaryStartDate: currentDate.format(DATE_TIME_FORMAT),
            salaryEndDate: currentDate.format(DATE_TIME_FORMAT),
            nextIncrementDate: currentDate.format(DATE_TIME_FORMAT),
            basic: 1,
            basicPercent: 1,
            houseRent: 1,
            houseRentPercent: 1,
            totalAllowance: 1,
            medicalAllowance: 1,
            medicalAllowancePercent: 1,
            convinceAllowance: 1,
            convinceAllowancePercent: 1,
            foodAllowance: 1,
            foodAllowancePercent: 1,
            specialAllowanceActiveStatus: 'BBBBBB',
            specialAllowance: 1,
            specialAllowancePercent: 1,
            specialAllowanceDescription: 'BBBBBB',
            insuranceActiveStatus: 'BBBBBB',
            insuranceAmount: 1,
            insurancePercent: 1,
            insuranceDescription: 'BBBBBB',
            insuranceProcessType: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            salaryStartDate: currentDate,
            salaryEndDate: currentDate,
            nextIncrementDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmployeeSalary', () => {
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
