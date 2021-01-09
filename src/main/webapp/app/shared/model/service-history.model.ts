import { Moment } from 'moment';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { ServiceStatus } from 'app/shared/model/enumerations/service-status.model';

export interface IServiceHistory {
  id?: number;
  employeeType?: EmployeeType;
  category?: EmployeeCategory;
  from?: Moment;
  to?: Moment;
  attachmentContentType?: string;
  attachment?: any;
  status?: ServiceStatus;
  department?: IDepartment;
  designation?: IDesignation;
  grade?: IGrade;
  employee?: IEmployee;
}

export class ServiceHistory implements IServiceHistory {
  constructor(
    public id?: number,
    public employeeType?: EmployeeType,
    public category?: EmployeeCategory,
    public from?: Moment,
    public to?: Moment,
    public attachmentContentType?: string,
    public attachment?: any,
    public status?: ServiceStatus,
    public department?: IDepartment,
    public designation?: IDesignation,
    public grade?: IGrade,
    public employee?: IEmployee
  ) {}
}
