import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';
import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleType } from 'app/shared/model/enumerations/vehicle-type.model';
import { ColourType } from 'app/shared/model/enumerations/colour-type.model';
import { CylinderNumber } from 'app/shared/model/enumerations/cylinder-number.model';
import { FuelType } from 'app/shared/model/enumerations/fuel-type.model';

describe('Service Tests', () => {
  describe('Vehicle Service', () => {
    let injector: TestBed;
    let service: VehicleService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicle;
    let expectedResult: IVehicle | IVehicle[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VehicleService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Vehicle(
        0,
        'AAAAAAA',
        'AAAAAAA',
        VehicleType.VEHICLE,
        'AAAAAAA',
        'AAAAAAA',
        ColourType.BLACK,
        CylinderNumber.ONE,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        FuelType.OCTANE,
        0,
        0,
        0,
        0
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

      it('should create a Vehicle', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Vehicle()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Vehicle', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            vehicleId: 'BBBBBB',
            type: 'BBBBBB',
            classOfVehicle: 'BBBBBB',
            typeOfBody: 'BBBBBB',
            colour: 'BBBBBB',
            numberOfCylinders: 'BBBBBB',
            engineNumber: 'BBBBBB',
            horsePower: 1,
            cubicCapacity: 1,
            noOfStandee: 'BBBBBB',
            unladenWeight: 1,
            prevRegnNo: 'BBBBBB',
            makersName: 'BBBBBB',
            makersCountry: 'BBBBBB',
            yearsOfManufacture: 1,
            chassisNumber: 'BBBBBB',
            fuelUsed: 'BBBBBB',
            rpm: 1,
            seats: 1,
            wheelBase: 1,
            maxLaden: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Vehicle', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            vehicleId: 'BBBBBB',
            type: 'BBBBBB',
            classOfVehicle: 'BBBBBB',
            typeOfBody: 'BBBBBB',
            colour: 'BBBBBB',
            numberOfCylinders: 'BBBBBB',
            engineNumber: 'BBBBBB',
            horsePower: 1,
            cubicCapacity: 1,
            noOfStandee: 'BBBBBB',
            unladenWeight: 1,
            prevRegnNo: 'BBBBBB',
            makersName: 'BBBBBB',
            makersCountry: 'BBBBBB',
            yearsOfManufacture: 1,
            chassisNumber: 'BBBBBB',
            fuelUsed: 'BBBBBB',
            rpm: 1,
            seats: 1,
            wheelBase: 1,
            maxLaden: 1,
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

      it('should delete a Vehicle', () => {
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
