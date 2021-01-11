import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';
import { PayrollGenerationType } from 'app/shared/model/enumerations/payroll-generation-type.model';

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
  type?: PayrollGenerationType;
  executedOn?: Moment;
  executedBy?: Moment;
  note?: any;
  employee?: IEmployee;
  monthlySalary?: IMonthlySalary;
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
    public type?: PayrollGenerationType,
    public executedOn?: Moment,
    public executedBy?: Moment,
    public note?: any,
    public employee?: IEmployee,
    public monthlySalary?: IMonthlySalary
  ) {}
}
