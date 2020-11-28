import { IDivision } from 'app/shared/model/division.model';
import { IDistrict } from 'app/shared/model/district.model';
import { IThana } from 'app/shared/model/thana.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { AddressType } from 'app/shared/model/enumerations/address-type.model';

export interface IAddress {
  id?: number;
  street?: string;
  area?: string;
  postCode?: number;
  addressType?: AddressType;
  division?: IDivision;
  district?: IDistrict;
  thana?: IThana;
  employee?: IEmployee;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public street?: string,
    public area?: string,
    public postCode?: number,
    public addressType?: AddressType,
    public division?: IDivision,
    public district?: IDistrict,
    public thana?: IThana,
    public employee?: IEmployee
  ) {}
}
