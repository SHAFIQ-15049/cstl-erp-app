import { Moment } from 'moment';
import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import { IAddress } from 'app/shared/model/address.model';
import { IEducationalInfo } from 'app/shared/model/educational-info.model';
import { ITraining } from 'app/shared/model/training.model';
import { IEmployeeAccount } from 'app/shared/model/employee-account.model';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { IServiceHistory } from 'app/shared/model/service-history.model';
import { ICompany } from 'app/shared/model/company.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

export interface IEmployee {
  id?: number;
  name?: string;
  empId?: string;
  globalId?: string;
  localId?: string;
  category?: EmployeeCategory;
  type?: EmployeeType;
  joiningDate?: Moment;
  status?: EmployeeStatus;
  terminationDate?: Moment;
  terminationReason?: any;
  personalInfo?: IPersonalInfo;
  addresses?: IAddress[];
  educationalInfos?: IEducationalInfo[];
  trainings?: ITraining[];
  employeeAccounts?: IEmployeeAccount[];
  jobHistories?: IJobHistory[];
  serviceHistories?: IServiceHistory[];
  company?: ICompany;
  department?: IDepartment;
  grade?: IGrade;
  designation?: IDesignation;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public empId?: string,
    public globalId?: string,
    public localId?: string,
    public category?: EmployeeCategory,
    public type?: EmployeeType,
    public joiningDate?: Moment,
    public status?: EmployeeStatus,
    public terminationDate?: Moment,
    public terminationReason?: any,
    public personalInfo?: IPersonalInfo,
    public addresses?: IAddress[],
    public educationalInfos?: IEducationalInfo[],
    public trainings?: ITraining[],
    public employeeAccounts?: IEmployeeAccount[],
    public jobHistories?: IJobHistory[],
    public serviceHistories?: IServiceHistory[],
    public company?: ICompany,
    public department?: IDepartment,
    public grade?: IGrade,
    public designation?: IDesignation
  ) {}
}