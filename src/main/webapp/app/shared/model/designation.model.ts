import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';

export interface IDesignation {
  id?: number;
  category?: EmployeeCategory;
  name?: string;
  shortName?: string;
  nameInBangla?: string;
  description?: any;
}

export class Designation implements IDesignation {
  constructor(
    public id?: number,
    public category?: EmployeeCategory,
    public name?: string,
    public shortName?: string,
    public nameInBangla?: string,
    public description?: any
  ) {}
}
