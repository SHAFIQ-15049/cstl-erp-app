import { Moment } from 'moment';
import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';

export interface IDutyLeave {
  id?: number;
  fromDate?: Moment;
  toDate?: Moment;
  employeeName?: string;
  employeeId?: number;
  attendanceSummaryDTO?: IAttendanceSummary;
}

export class DutyLeave implements IDutyLeave {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public toDate?: Moment,
    public employeeName?: string,
    public employeeId?: number,
    public attendanceSummaryDTO?: IAttendanceSummary
  ) {}
}
