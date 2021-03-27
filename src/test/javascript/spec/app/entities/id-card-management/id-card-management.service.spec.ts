import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IdCardManagementService } from 'app/entities/id-card-management/id-card-management.service';
import { IIdCardManagement, IdCardManagement } from 'app/shared/model/id-card-management.model';

describe('Service Tests', () => {
  describe('IdCardManagement Service', () => {
    let injector: TestBed;
    let service: IdCardManagementService;
    let httpMock: HttpTestingController;
    let elemDefault: IIdCardManagement;
    let expectedResult: IIdCardManagement | IIdCardManagement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IdCardManagementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IdCardManagement(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            issueDate: currentDate.format(DATE_FORMAT),
            validTill: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IdCardManagement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            issueDate: currentDate.format(DATE_FORMAT),
            validTill: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issueDate: currentDate,
            validTill: currentDate,
          },
          returnedFromService
        );

        service.create(new IdCardManagement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IdCardManagement', () => {
        const returnedFromService = Object.assign(
          {
            cardNo: 'BBBBBB',
            issueDate: currentDate.format(DATE_FORMAT),
            ticketNo: 'BBBBBB',
            validTill: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issueDate: currentDate,
            validTill: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IdCardManagement', () => {
        const returnedFromService = Object.assign(
          {
            cardNo: 'BBBBBB',
            issueDate: currentDate.format(DATE_FORMAT),
            ticketNo: 'BBBBBB',
            validTill: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            issueDate: currentDate,
            validTill: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IdCardManagement', () => {
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
