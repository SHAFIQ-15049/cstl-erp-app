import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

describe('Service Tests', () => {
  describe('Employee Service', () => {
    let injector: TestBed;
    let service: EmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployee;
    let expectedResult: IEmployee | IEmployee[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Employee(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        EmployeeCategory.TOP_LEVEL,
        EmployeeType.PERMANENT,
        currentDate,
        EmployeeStatus.ACTIVE,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            joiningDate: currentDate.format(DATE_FORMAT),
            terminationDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Employee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            joiningDate: currentDate.format(DATE_FORMAT),
            terminationDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joiningDate: currentDate,
            terminationDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Employee()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Employee', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            empId: 'BBBBBB',
            globalId: 'BBBBBB',
            attendanceMachineId: 'BBBBBB',
            localId: 'BBBBBB',
            category: 'BBBBBB',
            type: 'BBBBBB',
            joiningDate: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            terminationDate: currentDate.format(DATE_FORMAT),
            terminationReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joiningDate: currentDate,
            terminationDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Employee', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            empId: 'BBBBBB',
            globalId: 'BBBBBB',
            attendanceMachineId: 'BBBBBB',
            localId: 'BBBBBB',
            category: 'BBBBBB',
            type: 'BBBBBB',
            joiningDate: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            terminationDate: currentDate.format(DATE_FORMAT),
            terminationReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joiningDate: currentDate,
            terminationDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Employee', () => {
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
