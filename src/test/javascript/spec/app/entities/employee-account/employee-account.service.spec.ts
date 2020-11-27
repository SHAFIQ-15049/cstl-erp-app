import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmployeeAccountService } from 'app/entities/employee-account/employee-account.service';
import { IEmployeeAccount, EmployeeAccount } from 'app/shared/model/employee-account.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

describe('Service Tests', () => {
  describe('EmployeeAccount Service', () => {
    let injector: TestBed;
    let service: EmployeeAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployeeAccount;
    let expectedResult: IEmployeeAccount | IEmployeeAccount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeeAccountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EmployeeAccount(0, AccountType.BANK, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmployeeAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EmployeeAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmployeeAccount', () => {
        const returnedFromService = Object.assign(
          {
            accountType: 'BBBBBB',
            accountNo: 'BBBBBB',
            isSalaryAccount: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmployeeAccount', () => {
        const returnedFromService = Object.assign(
          {
            accountType: 'BBBBBB',
            accountNo: 'BBBBBB',
            isSalaryAccount: true,
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

      it('should delete a EmployeeAccount', () => {
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
