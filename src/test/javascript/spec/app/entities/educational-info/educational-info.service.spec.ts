import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EducationalInfoService } from 'app/entities/educational-info/educational-info.service';
import { IEducationalInfo, EducationalInfo } from 'app/shared/model/educational-info.model';

describe('Service Tests', () => {
  describe('EducationalInfo Service', () => {
    let injector: TestBed;
    let service: EducationalInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEducationalInfo;
    let expectedResult: IEducationalInfo | IEducationalInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EducationalInfoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EducationalInfo(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EducationalInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EducationalInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EducationalInfo', () => {
        const returnedFromService = Object.assign(
          {
            serial: 1,
            degree: 'BBBBBB',
            institution: 'BBBBBB',
            passingYear: 1,
            courseDuration: 1,
            attachment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EducationalInfo', () => {
        const returnedFromService = Object.assign(
          {
            serial: 1,
            degree: 'BBBBBB',
            institution: 'BBBBBB',
            passingYear: 1,
            courseDuration: 1,
            attachment: 'BBBBBB',
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

      it('should delete a EducationalInfo', () => {
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
