import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';

export interface IGrade {
  id?: number;
  category?: EmployeeCategory;
  name?: string;
  description?: any;
  initialSalary?: number;
}

export class Grade implements IGrade {
  constructor(
    public id?: number,
    public category?: EmployeeCategory,
    public name?: string,
    public description?: any,
    public initialSalary?: number
  ) {}
}
