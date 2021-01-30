import { Moment } from 'moment';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';

export interface ILeaveBalance {
  id?: number;
  totalDays?: number;
  remainingDays?: number;
  employeeId?: number;
  employeeName?: string;
  employeeJoiningDate?: Moment;
  leaveTypeId?: number;
  leaveTypeName?: string;
  acceptedLeaveApplications?: ILeaveApplication[];
}

export class LeaveBalance implements ILeaveBalance {
  constructor(
    public id?: number,
    public totalDays?: number,
    public remainingDays?: number,
    public employeeId?: number,
    public employeeName?: string,
    public employeeJoiningDate?: Moment,
    public leaveTypeId?: number,
    public leaveTypeName?: string,
    public acceptedLeaveApplications?: ILeaveApplication[]
  ) {}
}
