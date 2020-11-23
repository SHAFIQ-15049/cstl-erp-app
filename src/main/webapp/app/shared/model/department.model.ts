export interface IDepartment {
  id?: number;
  name?: string;
  shortName?: string;
  nameInBangla?: string;
  description?: any;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public name?: string,
    public shortName?: string,
    public nameInBangla?: string,
    public description?: any
  ) {}
}
