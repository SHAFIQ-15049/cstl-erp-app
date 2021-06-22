import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';

describe('Service Tests', () => {
  describe('Customer Service', () => {
    let injector: TestBed;
    let service: CustomerService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomer;
    let expectedResult: ICustomer | ICustomer[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CustomerService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Customer(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        GenderType.MALE,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Customer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.create(new Customer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Customer', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            fatherOrHusband: 'BBBBBB',
            address: 'BBBBBB',
            sex: 'BBBBBB',
            phoneNo: 'BBBBBB',
            nationality: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            guardiansName: 'BBBBBB',
            chassisNo: 'BBBBBB',
            engineNo: 'BBBBBB',
            yearsOfMfg: 1,
            preRegnNo: 'BBBBBB',
            poOrBank: 'BBBBBB',
            voterIdNo: 'BBBBBB',
            voterIdAttachment: 'BBBBBB',
            passportAttachment: 'BBBBBB',
            birthCertificateAttachment: 'BBBBBB',
            gassOrWaterOrElectricityBill: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Customer', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            fatherOrHusband: 'BBBBBB',
            address: 'BBBBBB',
            sex: 'BBBBBB',
            phoneNo: 'BBBBBB',
            nationality: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            guardiansName: 'BBBBBB',
            chassisNo: 'BBBBBB',
            engineNo: 'BBBBBB',
            yearsOfMfg: 1,
            preRegnNo: 'BBBBBB',
            poOrBank: 'BBBBBB',
            voterIdNo: 'BBBBBB',
            voterIdAttachment: 'BBBBBB',
            passportAttachment: 'BBBBBB',
            birthCertificateAttachment: 'BBBBBB',
            gassOrWaterOrElectricityBill: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Customer', () => {
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
