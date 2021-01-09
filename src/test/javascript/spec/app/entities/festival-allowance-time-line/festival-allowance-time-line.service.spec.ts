import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FestivalAllowanceTimeLineService } from 'app/entities/festival-allowance-time-line/festival-allowance-time-line.service';
import { IFestivalAllowanceTimeLine, FestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

describe('Service Tests', () => {
  describe('FestivalAllowanceTimeLine Service', () => {
    let injector: TestBed;
    let service: FestivalAllowanceTimeLineService;
    let httpMock: HttpTestingController;
    let elemDefault: IFestivalAllowanceTimeLine;
    let expectedResult: IFestivalAllowanceTimeLine | IFestivalAllowanceTimeLine[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FestivalAllowanceTimeLineService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FestivalAllowanceTimeLine(0, 0, MonthType.JANUARY);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FestivalAllowanceTimeLine', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FestivalAllowanceTimeLine()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FestivalAllowanceTimeLine', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FestivalAllowanceTimeLine', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FestivalAllowanceTimeLine', () => {
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
