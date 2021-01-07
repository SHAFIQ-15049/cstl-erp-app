import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DefaultAllowanceService } from 'app/entities/default-allowance/default-allowance.service';
import { IDefaultAllowance, DefaultAllowance } from 'app/shared/model/default-allowance.model';
import { ActiveStatus } from 'app/shared/model/enumerations/active-status.model';

describe('Service Tests', () => {
  describe('DefaultAllowance Service', () => {
    let injector: TestBed;
    let service: DefaultAllowanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IDefaultAllowance;
    let expectedResult: IDefaultAllowance | IDefaultAllowance[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DefaultAllowanceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DefaultAllowance(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ActiveStatus.ACTIVE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DefaultAllowance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DefaultAllowance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DefaultAllowance', () => {
        const returnedFromService = Object.assign(
          {
            basic: 1,
            basicPercent: 1,
            totalAllowance: 1,
            medicalAllowance: 1,
            medicalAllowancePercent: 1,
            convinceAllowance: 1,
            convinceAllowancePercent: 1,
            foodAllowance: 1,
            foodAllowancePercent: 1,
            festivalAllowance: 1,
            festivalAllowancePercent: 1,
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DefaultAllowance', () => {
        const returnedFromService = Object.assign(
          {
            basic: 1,
            basicPercent: 1,
            totalAllowance: 1,
            medicalAllowance: 1,
            medicalAllowancePercent: 1,
            convinceAllowance: 1,
            convinceAllowancePercent: 1,
            foodAllowance: 1,
            foodAllowancePercent: 1,
            festivalAllowance: 1,
            festivalAllowancePercent: 1,
            status: 'BBBBBB',
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

      it('should delete a DefaultAllowance', () => {
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
