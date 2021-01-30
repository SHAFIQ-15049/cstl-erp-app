import { Moment } from 'moment';
import { AttendanceMarkedAs } from 'app/shared/model/enumerations/attendance-marked-as.model';
import { LeaveAppliedStatus } from 'app/shared/model/enumerations/leave-applied-status.model';

export interface IAttendanceSummary {
  serialNo?: number;
  inTime?: Moment;
  outTime?: Moment;
  diff?: number;
  overTime?: number;
  attendanceMarkedAs?: AttendanceMarkedAs;
  leaveApplied?: LeaveAppliedStatus;
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
    public attendanceMarkedAs?: AttendanceMarkedAs,
    public leaveApplied?: LeaveAppliedStatus,
    public employeeId?: number,
    public employeeName?: number,
    public employeeMachineId?: number,
    public employeeSalaryId?: number
  ) {}
}
