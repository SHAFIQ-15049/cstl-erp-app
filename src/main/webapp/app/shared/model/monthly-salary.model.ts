import { Moment } from 'moment';
import { IMonthlySalaryDtl } from 'app/shared/model/monthly-salary-dtl.model';
import { IDepartment } from 'app/shared/model/department.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IMonthlySalary {
  id?: number;
  year?: number;
  month?: MonthType;
  fromDate?: Moment;
  toDate?: Moment;
  status?: SalaryExecutionStatus;
  executedOn?: Moment;
  executedBy?: string;
  monthlySalaryDtls?: IMonthlySalaryDtl[];
  department?: IDepartment;
}

export class MonthlySalary implements IMonthlySalary {
  constructor(
    public id?: number,
    public year?: number,
    public month?: MonthType,
    public fromDate?: Moment,
    public toDate?: Moment,
    public status?: SalaryExecutionStatus,
    public executedOn?: Moment,
    public executedBy?: string,
    public monthlySalaryDtls?: IMonthlySalaryDtl[],
    public department?: IDepartment
  ) {}
}
