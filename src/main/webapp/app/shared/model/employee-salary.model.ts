import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { ActiveStatus } from 'app/shared/model/enumerations/active-status.model';
import { InsuranceProcessType } from 'app/shared/model/enumerations/insurance-process-type.model';

export interface IEmployeeSalary {
  id?: number;
  gross?: number;
  incrementAmount?: number;
  incrementPercentage?: number;
  salaryStartDate?: Moment;
  salaryEndDate?: Moment;
  nextIncrementDate?: Moment;
  basic?: number;
  basicPercent?: number;
  houseRent?: number;
  houseRentPercent?: number;
  totalAllowance?: number;
  medicalAllowance?: number;
  medicalAllowancePercent?: number;
  convinceAllowance?: number;
  convinceAllowancePercent?: number;
  foodAllowance?: number;
  foodAllowancePercent?: number;
  specialAllowanceActiveStatus?: ActiveStatus;
  specialAllowance?: number;
  specialAllowancePercent?: number;
  specialAllowanceDescription?: any;
  insuranceActiveStatus?: ActiveStatus;
  insuranceAmount?: number;
  insurancePercent?: number;
  insuranceDescription?: any;
  insuranceProcessType?: InsuranceProcessType;
  status?: ActiveStatus;
  employee?: IEmployee;
}

export class EmployeeSalary implements IEmployeeSalary {
  constructor(
    public id?: number,
    public gross?: number,
    public incrementAmount?: number,
    public incrementPercentage?: number,
    public salaryStartDate?: Moment,
    public salaryEndDate?: Moment,
    public nextIncrementDate?: Moment,
    public basic?: number,
    public basicPercent?: number,
    public houseRent?: number,
    public houseRentPercent?: number,
    public totalAllowance?: number,
    public medicalAllowance?: number,
    public medicalAllowancePercent?: number,
    public convinceAllowance?: number,
    public convinceAllowancePercent?: number,
    public foodAllowance?: number,
    public foodAllowancePercent?: number,
    public specialAllowanceActiveStatus?: ActiveStatus,
    public specialAllowance?: number,
    public specialAllowancePercent?: number,
    public specialAllowanceDescription?: any,
    public insuranceActiveStatus?: ActiveStatus,
    public insuranceAmount?: number,
    public insurancePercent?: number,
    public insuranceDescription?: any,
    public insuranceProcessType?: InsuranceProcessType,
    public status?: ActiveStatus,
    public employee?: IEmployee
  ) {}
}
