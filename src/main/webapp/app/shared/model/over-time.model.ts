import { Moment } from 'moment';
import { IDesignation } from 'app/shared/model/designation.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

export interface IOverTime {
  id?: number;
  year?: number;
  month?: MonthType;
  fromDate?: Moment;
  toDate?: Moment;
  totalOverTime?: number;
  officialOverTime?: number;
  extraOverTime?: number;
  totalAmount?: number;
  officialAmount?: number;
  extraAmount?: number;
  note?: any;
  executedOn?: Moment;
  executedBy?: string;
  designation?: IDesignation;
  employee?: IEmployee;
}

export class OverTime implements IOverTime {
  constructor(
    public id?: number,
    public year?: number,
    public month?: MonthType,
    public fromDate?: Moment,
    public toDate?: Moment,
    public totalOverTime?: number,
    public officialOverTime?: number,
    public extraOverTime?: number,
    public totalAmount?: number,
    public officialAmount?: number,
    public extraAmount?: number,
    public note?: any,
    public executedOn?: Moment,
    public executedBy?: string,
    public designation?: IDesignation,
    public employee?: IEmployee
  ) {}
}
