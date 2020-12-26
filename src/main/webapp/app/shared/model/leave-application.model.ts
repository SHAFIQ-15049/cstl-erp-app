import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveApplicationStatus } from 'app/shared/model/enumerations/leave-application-status.model';

export interface ILeaveApplication {
  id?: number;
  from?: Moment;
  to?: Moment;
  totalDays?: number;
  status?: LeaveApplicationStatus;
  reason?: string;
  appliedBy?: IUser;
  actionTakenBy?: IUser;
  leaveType?: ILeaveType;
}

export class LeaveApplication implements ILeaveApplication {
  constructor(
    public id?: number,
    public from?: Moment,
    public to?: Moment,
    public totalDays?: number,
    public status?: LeaveApplicationStatus,
    public reason?: string,
    public appliedBy?: IUser,
    public actionTakenBy?: IUser,
    public leaveType?: ILeaveType
  ) {}
}
