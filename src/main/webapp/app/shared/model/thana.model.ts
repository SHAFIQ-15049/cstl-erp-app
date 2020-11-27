import { IDistrict } from 'app/shared/model/district.model';

export interface IThana {
  id?: number;
  name?: string;
  district?: IDistrict;
}

export class Thana implements IThana {
  constructor(public id?: number, public name?: string, public district?: IDistrict) {}
}
