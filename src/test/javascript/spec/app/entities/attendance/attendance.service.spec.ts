import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AttendanceService } from 'app/entities/attendance/attendance.service';
import { Attendance, IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceMarkedAs } from 'app/shared/model/enumerations/attendance-marked-as.model';
import { LeaveAppliedStatus } from 'app/shared/model/enumerations/leave-applied-status.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';

describe('Service Tests', () => {
  describe('Attendance Service', () => {
    let injector: TestBed;
    let service: AttendanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IAttendance;
    let expectedResult: IAttendance | IAttendance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AttendanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Attendance(
        0,
        currentDate,
        'AAAAAAA',
        AttendanceMarkedAs.R,
        LeaveAppliedStatus.YES,
        'AAAAAAA',
        EmployeeCategory.TOP_LEVEL,
        EmployeeType.PERMANENT
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            attendanceTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Attendance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            attendanceTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attendanceTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Attendance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Attendance', () => {
        const returnedFromService = Object.assign(
          {
            attendanceTime: currentDate.format(DATE_TIME_FORMAT),
            machineNo: 'BBBBBB',
            markedAs: 'BBBBBB',
            leaveApplied: 'BBBBBB',
            employeeMachineId: 'BBBBBB',
            employeeCategory: 'BBBBBB',
            employeeType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attendanceTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Attendance', () => {
        const returnedFromService = Object.assign(
          {
            attendanceTime: currentDate.format(DATE_TIME_FORMAT),
            machineNo: 'BBBBBB',
            markedAs: 'BBBBBB',
            leaveApplied: 'BBBBBB',
            employeeMachineId: 'BBBBBB',
            employeeCategory: 'BBBBBB',
            employeeType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attendanceTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Attendance', () => {
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
