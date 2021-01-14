import { Moment } from 'moment';

export interface IAttendanceSummary {
  id?: number;
  inTime?: Moment;
  outTime?: Moment;
  diff?: string;
  overTime?: string;
  employeeId?: number;
  employeeName?: number;
  employeeMachineId?: number;
  employeeSalaryId?: number;
}

export class AttendanceSummary implements IAttendanceSummary {
  constructor(
    public id?: number,
    public inTime?: Moment,
    public outTime?: Moment,
    public diff?: string,
    public overTime?: string,
    public employeeId?: number,
    public employeeName?: number,
    public employeeMachineId?: number,
    public employeeSalaryId?: number
  ) {}
}
