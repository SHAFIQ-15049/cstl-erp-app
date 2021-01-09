import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ServiceHistoryService } from 'app/entities/service-history/service-history.service';
import { IServiceHistory, ServiceHistory } from 'app/shared/model/service-history.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { ServiceStatus } from 'app/shared/model/enumerations/service-status.model';

describe('Service Tests', () => {
  describe('ServiceHistory Service', () => {
    let injector: TestBed;
    let service: ServiceHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IServiceHistory;
    let expectedResult: IServiceHistory | IServiceHistory[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ServiceHistoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ServiceHistory(
        0,
        EmployeeType.PERMANENT,
        EmployeeCategory.TOP_LEVEL,
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        ServiceStatus.ACTIVE
      );
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

      it('should create a ServiceHistory', () => {
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

        service.create(new ServiceHistory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ServiceHistory', () => {
        const returnedFromService = Object.assign(
          {
            employeeType: 'BBBBBB',
            category: 'BBBBBB',
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            attachment: 'BBBBBB',
            status: 'BBBBBB',
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

      it('should return a list of ServiceHistory', () => {
        const returnedFromService = Object.assign(
          {
            employeeType: 'BBBBBB',
            category: 'BBBBBB',
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            attachment: 'BBBBBB',
            status: 'BBBBBB',
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

      it('should delete a ServiceHistory', () => {
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
