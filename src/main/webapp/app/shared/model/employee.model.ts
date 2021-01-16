import { Moment } from 'moment';
import { IPartialSalary } from 'app/shared/model/partial-salary.model';
import { IOverTime } from 'app/shared/model/over-time.model';
import { IFine } from 'app/shared/model/fine.model';
import { IAdvance } from 'app/shared/model/advance.model';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { IEducationalInfo } from 'app/shared/model/educational-info.model';
import { ITraining } from 'app/shared/model/training.model';
import { IEmployeeAccount } from 'app/shared/model/employee-account.model';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { IServiceHistory } from 'app/shared/model/service-history.model';
import { ICompany } from 'app/shared/model/company.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { ILine } from 'app/shared/model/line.model';
import { IAddress } from 'app/shared/model/address.model';
import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

export interface IEmployee {
  id?: number;
  name?: string;
  empId?: string;
  globalId?: string;
  attendanceMachineId?: string;
  localId?: string;
  category?: EmployeeCategory;
  type?: EmployeeType;
  joiningDate?: Moment;
  status?: EmployeeStatus;
  terminationDate?: Moment;
  terminationReason?: any;
  partialSalaries?: IPartialSalary[];
  overTimes?: IOverTime[];
  fines?: IFine[];
  advances?: IAdvance[];
  employeeSalaries?: IEmployeeSalary[];
  educationalInfos?: IEducationalInfo[];
  trainings?: ITraining[];
  employeeAccounts?: IEmployeeAccount[];
  jobHistories?: IJobHistory[];
  serviceHistories?: IServiceHistory[];
  company?: ICompany;
  department?: IDepartment;
  grade?: IGrade;
  designation?: IDesignation;
  line?: ILine;
  address?: IAddress;
  personalInfo?: IPersonalInfo;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public empId?: string,
    public globalId?: string,
    public attendanceMachineId?: string,
    public localId?: string,
    public category?: EmployeeCategory,
    public type?: EmployeeType,
    public joiningDate?: Moment,
    public status?: EmployeeStatus,
    public terminationDate?: Moment,
    public terminationReason?: any,
    public partialSalaries?: IPartialSalary[],
    public overTimes?: IOverTime[],
    public fines?: IFine[],
    public advances?: IAdvance[],
    public employeeSalaries?: IEmployeeSalary[],
    public educationalInfos?: IEducationalInfo[],
    public trainings?: ITraining[],
    public employeeAccounts?: IEmployeeAccount[],
    public jobHistories?: IJobHistory[],
    public serviceHistories?: IServiceHistory[],
    public company?: ICompany,
    public department?: IDepartment,
    public grade?: IGrade,
    public designation?: IDesignation,
    public line?: ILine,
    public address?: IAddress,
    public personalInfo?: IPersonalInfo
  ) {}
}
