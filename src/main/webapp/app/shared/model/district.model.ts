import { IDivision } from 'app/shared/model/division.model';

export interface IDistrict {
  id?: number;
  name?: string;
  bangla?: string;
  division?: IDivision;
}

export class District implements IDistrict {
  constructor(public id?: number, public name?: string, public bangla?: string, public division?: IDivision) {}
}
