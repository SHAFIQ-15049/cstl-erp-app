import { IDivision } from 'app/shared/model/division.model';
import { IDistrict } from 'app/shared/model/district.model';
import { IThana } from 'app/shared/model/thana.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IAddress {
  id?: number;
  presentThanaTxt?: string;
  presentStreet?: string;
  presentStreetBangla?: string;
  presentArea?: string;
  presentAreaBangla?: string;
  presentPostCode?: number;
  presentPostCodeBangla?: string;
  permanentThanaTxt?: string;
  permanentStreet?: string;
  permanentStreetBangla?: string;
  permanentArea?: string;
  permanentAreaBangla?: string;
  permanentPostCode?: number;
  permenentPostCodeBangla?: string;
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
    public presentStreetBangla?: string,
    public presentArea?: string,
    public presentAreaBangla?: string,
    public presentPostCode?: number,
    public presentPostCodeBangla?: string,
    public permanentThanaTxt?: string,
    public permanentStreet?: string,
    public permanentStreetBangla?: string,
    public permanentArea?: string,
    public permanentAreaBangla?: string,
    public permanentPostCode?: number,
    public permenentPostCodeBangla?: string,
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
