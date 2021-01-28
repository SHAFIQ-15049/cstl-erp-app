import { Moment } from 'moment';

export interface IDutyLeave {
  id?: number;
  fromDate?: Moment;
  toDate?: Moment;
  employeeName?: string;
  employeeId?: number;
}

export class DutyLeave implements IDutyLeave {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public toDate?: Moment,
    public employeeName?: string,
    public employeeId?: number
  ) {}
}
