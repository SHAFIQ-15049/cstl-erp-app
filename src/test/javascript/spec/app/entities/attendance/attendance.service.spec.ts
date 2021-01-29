import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AttendanceService } from 'app/entities/attendance/attendance.service';
import { Attendance, IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceMarkedAs } from 'app/shared/model/enumerations/attendance-marked-as.model';
import { LeaveAppliedStatus } from 'app/shared/model/enumerations/leave-applied-status.model';

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

      elemDefault = new Attendance(0, currentDate, 'AAAAAAA', AttendanceMarkedAs.R, LeaveAppliedStatus.YES);
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

      it('should return a list of Attendance', () => {
        const returnedFromService = Object.assign(
          {
            attendanceTime: currentDate.format(DATE_TIME_FORMAT),
            machineNo: 'BBBBBB',
            markedAs: 'BBBBBB',
            leaveApplied: 'BBBBBB',
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
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
