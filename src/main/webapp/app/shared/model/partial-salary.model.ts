import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IPartialSalary {
  id?: number;
  year?: number;
  month?: MonthType;
  totalMonthDays?: number;
  fromDate?: Moment;
  toDate?: Moment;
  gross?: number;
  basic?: number;
  basicPercent?: number;
  houseRent?: number;
  houseRentPercent?: number;
  medicalAllowance?: number;
  medicalAllowancePercent?: number;
  convinceAllowance?: number;
  convinceAllowancePercent?: number;
  foodAllowance?: number;
  foodAllowancePercent?: number;
  fine?: number;
  advance?: number;
  status?: SalaryExecutionStatus;
  executedOn?: Moment;
  executedBy?: string;
  note?: any;
  employee?: IEmployee;
}

export class PartialSalary implements IPartialSalary {
  constructor(
    public id?: number,
    public year?: number,
    public month?: MonthType,
    public totalMonthDays?: number,
    public fromDate?: Moment,
    public toDate?: Moment,
    public gross?: number,
    public basic?: number,
    public basicPercent?: number,
    public houseRent?: number,
    public houseRentPercent?: number,
    public medicalAllowance?: number,
    public medicalAllowancePercent?: number,
    public convinceAllowance?: number,
    public convinceAllowancePercent?: number,
    public foodAllowance?: number,
    public foodAllowancePercent?: number,
    public fine?: number,
    public advance?: number,
    public status?: SalaryExecutionStatus,
    public executedOn?: Moment,
    public executedBy?: string,
    public note?: any,
    public employee?: IEmployee
  ) {}
}
