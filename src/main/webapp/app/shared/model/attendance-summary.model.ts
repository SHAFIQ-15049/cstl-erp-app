import { Moment } from 'moment';

export interface IAttendanceSummary {
  serialNo?: number;
  inTime?: Moment;
  outTime?: Moment;
  diff?: number;
  overTime?: number;
  employeeId?: number;
  employeeName?: number;
  employeeMachineId?: number;
  employeeSalaryId?: number;
}

export class AttendanceSummary implements IAttendanceSummary {
  constructor(
    public serialNo?: number,
    public inTime?: Moment,
    public outTime?: Moment,
    public diff?: number,
    public overTime?: number,
    public employeeId?: number,
    public employeeName?: number,
    public employeeMachineId?: number,
    public employeeSalaryId?: number
  ) {}
}