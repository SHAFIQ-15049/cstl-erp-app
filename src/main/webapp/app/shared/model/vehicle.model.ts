import { ICustomer } from 'app/shared/model/customer.model';
import { VehicleType } from 'app/shared/model/enumerations/vehicle-type.model';
import { ColourType } from 'app/shared/model/enumerations/colour-type.model';
import { CylinderNumber } from 'app/shared/model/enumerations/cylinder-number.model';
import { FuelType } from 'app/shared/model/enumerations/fuel-type.model';

export interface IVehicle {
  id?: number;
  name?: string;
  vehicleId?: string;
  type?: VehicleType;
  classOfVehicle?: string;
  typeOfBody?: string;
  colour?: ColourType;
  numberOfCylinders?: CylinderNumber;
  engineNumber?: string;
  horsePower?: number;
  cubicCapacity?: number;
  noOfStandee?: string;
  unladenWeight?: number;
  prevRegnNo?: string;
  makersName?: string;
  makersCountry?: string;
  yearsOfManufacture?: number;
  chassisNumber?: string;
  fuelUsed?: FuelType;
  rpm?: number;
  seats?: number;
  wheelBase?: number;
  maxLaden?: number;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public name?: string,
    public vehicleId?: string,
    public type?: VehicleType,
    public classOfVehicle?: string,
    public typeOfBody?: string,
    public colour?: ColourType,
    public numberOfCylinders?: CylinderNumber,
    public engineNumber?: string,
    public horsePower?: number,
    public cubicCapacity?: number,
    public noOfStandee?: string,
    public unladenWeight?: number,
    public prevRegnNo?: string,
    public makersName?: string,
    public makersCountry?: string,
    public yearsOfManufacture?: number,
    public chassisNumber?: string,
    public fuelUsed?: FuelType,
    public rpm?: number,
    public seats?: number,
    public wheelBase?: number,
    public maxLaden?: number
  ) {}
}
