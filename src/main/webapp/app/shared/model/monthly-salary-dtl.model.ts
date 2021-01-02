import { Moment } from 'moment';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IMonthlySalaryDtl {
  id?: number;
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
  executedBy?: Moment;
  note?: any;
  monthlySalary?: IMonthlySalary;
  employee?: IEmployee;
}

export class MonthlySalaryDtl implements IMonthlySalaryDtl {
  constructor(
    public id?: number,
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
    public executedBy?: Moment,
    public note?: any,
    public monthlySalary?: IMonthlySalary,
    public employee?: IEmployee
  ) {}
}
