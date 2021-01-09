import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HolidayService } from 'app/entities/holiday/holiday.service';
import { IHoliday, Holiday } from 'app/shared/model/holiday.model';

describe('Service Tests', () => {
  describe('Holiday Service', () => {
    let injector: TestBed;
    let service: HolidayService;
    let httpMock: HttpTestingController;
    let elemDefault: IHoliday;
    let expectedResult: IHoliday | IHoliday[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HolidayService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Holiday(0, currentDate, currentDate, 0);
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

      it('should create a Holiday', () => {
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

        service.create(new Holiday()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Holiday', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            totalDays: 1,
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

      it('should return a list of Holiday', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            totalDays: 1,
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

      it('should delete a Holiday', () => {
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
