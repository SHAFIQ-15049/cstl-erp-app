import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AddressService } from 'app/entities/address/address.service';
import { IAddress, Address } from 'app/shared/model/address.model';

describe('Service Tests', () => {
  describe('Address Service', () => {
    let injector: TestBed;
    let service: AddressService;
    let httpMock: HttpTestingController;
    let elemDefault: IAddress;
    let expectedResult: IAddress | IAddress[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AddressService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Address(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Address', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Address()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Address', () => {
        const returnedFromService = Object.assign(
          {
            presentThanaTxt: 'BBBBBB',
            presentStreet: 'BBBBBB',
            presentStreetBangla: 'BBBBBB',
            presentArea: 'BBBBBB',
            presentAreaBangla: 'BBBBBB',
            presentPostCode: 1,
            presentPostCodeBangla: 'BBBBBB',
            permanentThanaTxt: 'BBBBBB',
            permanentStreet: 'BBBBBB',
            permanentStreetBangla: 'BBBBBB',
            permanentArea: 'BBBBBB',
            permanentAreaBangla: 'BBBBBB',
            permanentPostCode: 1,
            permenentPostCodeBangla: 'BBBBBB',
            isSame: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Address', () => {
        const returnedFromService = Object.assign(
          {
            presentThanaTxt: 'BBBBBB',
            presentStreet: 'BBBBBB',
            presentStreetBangla: 'BBBBBB',
            presentArea: 'BBBBBB',
            presentAreaBangla: 'BBBBBB',
            presentPostCode: 1,
            presentPostCodeBangla: 'BBBBBB',
            permanentThanaTxt: 'BBBBBB',
            permanentStreet: 'BBBBBB',
            permanentStreetBangla: 'BBBBBB',
            permanentArea: 'BBBBBB',
            permanentAreaBangla: 'BBBBBB',
            permanentPostCode: 1,
            permenentPostCodeBangla: 'BBBBBB',
            isSame: true,
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

      it('should delete a Address', () => {
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
