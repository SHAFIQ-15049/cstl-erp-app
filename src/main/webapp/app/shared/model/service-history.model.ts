import { Moment } from 'moment';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';

export interface IServiceHistory {
  id?: number;
  employeeType?: EmployeeType;
  from?: Moment;
  to?: Moment;
  attachmentContentType?: string;
  attachment?: any;
  department?: IDepartment;
  designation?: IDesignation;
  grade?: IGrade;
  employee?: IEmployee;
}

export class ServiceHistory implements IServiceHistory {
  constructor(
    public id?: number,
    public employeeType?: EmployeeType,
    public from?: Moment,
    public to?: Moment,
    public attachmentContentType?: string,
    public attachment?: any,
    public department?: IDepartment,
    public designation?: IDesignation,
    public grade?: IGrade,
    public employee?: IEmployee
  ) {}
}
