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
  departmentId?: number;
  departmentName?: string;
  employeeId?: number;
  employeeName?: number;
  employeeMachineId?: number;
  employeeSalaryId?: number;
  attendanceStatus?: string;
  attendanceDate?: Moment;
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
    public departmentId?: number,
    public departmentName?: string,
    public employeeId?: number,
    public employeeName?: number,
    public employeeMachineId?: number,
    public employeeSalaryId?: number,
    public attendanceStatus?: string,
    public attendanceDate?: Moment
  ) {}
}
