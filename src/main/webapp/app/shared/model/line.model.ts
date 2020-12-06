import { IDepartment } from 'app/shared/model/department.model';

export interface ILine {
  id?: number;
  name?: string;
  description?: any;
  department?: IDepartment;
}

export class Line implements ILine {
  constructor(public id?: number, public name?: string, public description?: any, public department?: IDepartment) {}
}
