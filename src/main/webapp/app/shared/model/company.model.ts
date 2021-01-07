export interface ICompany {
  id?: number;
  name?: string;
  shortName?: string;
  nameInBangla?: string;
  description?: any;
  address?: any;
  phone?: string;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public shortName?: string,
    public nameInBangla?: string,
    public description?: any,
    public address?: any,
    public phone?: string
  ) {}
}
