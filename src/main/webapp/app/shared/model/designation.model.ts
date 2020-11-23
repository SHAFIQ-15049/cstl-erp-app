export interface IDesignation {
  id?: number;
  name?: string;
  shortName?: string;
  nameInBangla?: string;
  description?: any;
}

export class Designation implements IDesignation {
  constructor(
    public id?: number,
    public name?: string,
    public shortName?: string,
    public nameInBangla?: string,
    public description?: any
  ) {}
}
