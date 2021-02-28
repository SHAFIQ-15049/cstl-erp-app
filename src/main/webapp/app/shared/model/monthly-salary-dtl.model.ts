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
  totalWorkingDays?: number;
  regularLeave?: number;
  sickLeave?: number;
  compensationLeave?: number;
  festivalLeave?: number;
  weeklyLeave?: number;
  present?: number;
  absent?: number;
  totalMonthDays?: number;
  overTimeHour?: number;
  overTimeSalaryHourly?: number;
  overTimeSalary?: number;
  presentBonus?: number;
  absentFine?: number;
  stampPrice?: number;
  tax?: number;
  others?: number;
  totalPayable?: number;
  status?: SalaryExecutionStatus;
  type?: PayrollGenerationType;
  executedOn?: Moment;
  executedBy?: string;
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
    public totalWorkingDays?: number,
    public regularLeave?: number,
    public sickLeave?: number,
    public compensationLeave?: number,
    public festivalLeave?: number,
    public weeklyLeave?: number,
    public present?: number,
    public absent?: number,
    public totalMonthDays?: number,
    public overTimeHour?: number,
    public overTimeSalaryHourly?: number,
    public overTimeSalary?: number,
    public presentBonus?: number,
    public absentFine?: number,
    public stampPrice?: number,
    public tax?: number,
    public others?: number,
    public totalPayable?: number,
    public status?: SalaryExecutionStatus,
    public type?: PayrollGenerationType,
    public executedOn?: Moment,
    public executedBy?: string,
    public note?: any,
    public employee?: IEmployee,
    public monthlySalary?: IMonthlySalary
  ) {}
}
