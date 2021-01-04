import { Moment } from 'moment';
import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IMonthlySalary {
  id?: number;
  year?: number;
  month?: MonthType;
  status?: SalaryExecutionStatus;
  executedOn?: Moment;
  executedBy?: Moment;
  monthlySalaryDtls?: IMonthlySalaryDtl[];
  designation?: IDesignation;
}

export class MonthlySalary implements IMonthlySalary {
  constructor(
    public id?: number,
    public year?: number,
    public month?: MonthType,
    public status?: SalaryExecutionStatus,
    public executedOn?: Moment,
    public executedBy?: Moment,
    public monthlySalaryDtls?: IMonthlySalaryDtl[],
    public designation?: IDesignation
  ) {}
}
