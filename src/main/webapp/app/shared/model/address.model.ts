import { IDivision } from 'app/shared/model/division.model';
import { IDistrict } from 'app/shared/model/district.model';
import { IThana } from 'app/shared/model/thana.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IAddress {
  id?: number;
  presentThanaTxt?: string;
  presentStreet?: string;
  presentArea?: string;
  presentPostCode?: number;
  permanentThanaTxt?: string;
  permanentStreet?: string;
  permanentArea?: string;
  permanentPostCode?: number;
  isSame?: boolean;
  presentDivision?: IDivision;
  presentDistrict?: IDistrict;
  presentThana?: IThana;
  permanentDivision?: IDivision;
  permanentDistrict?: IDistrict;
  permanentThana?: IThana;
  employee?: IEmployee;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public presentThanaTxt?: string,
    public presentStreet?: string,
    public presentArea?: string,
    public presentPostCode?: number,
    public permanentThanaTxt?: string,
    public permanentStreet?: string,
    public permanentArea?: string,
    public permanentPostCode?: number,
    public isSame?: boolean,
    public presentDivision?: IDivision,
    public presentDistrict?: IDistrict,
    public presentThana?: IThana,
    public permanentDivision?: IDivision,
    public permanentDistrict?: IDistrict,
    public permanentThana?: IThana,
    public employee?: IEmployee
  ) {
    this.isSame = this.isSame || false;
  }
}
