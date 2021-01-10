import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationStatus } from 'app/shared/model/enumerations/leave-application-status.model';

describe('Service Tests', () => {
  describe('LeaveApplication Service', () => {
    let injector: TestBed;
    let service: LeaveApplicationService;
    let httpMock: HttpTestingController;
    let elemDefault: ILeaveApplication;
    let expectedResult: ILeaveApplication | ILeaveApplication[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LeaveApplicationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LeaveApplication(0, currentDate, currentDate, 0, LeaveApplicationStatus.APPLIED, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LeaveApplication', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
          },
          returnedFromService
        );

        service.create(new LeaveApplication()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LeaveApplication', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            totalDays: 1,
            status: 'BBBBBB',
            reason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LeaveApplication', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            totalDays: 1,
            status: 'BBBBBB',
            reason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LeaveApplication', () => {
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
