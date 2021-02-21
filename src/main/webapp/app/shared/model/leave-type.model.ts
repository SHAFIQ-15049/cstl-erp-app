import { LeaveTypeName } from 'app/shared/model/enumerations/leave-type-name.model';

export interface ILeaveType {
  id?: number;
  name?: LeaveTypeName;
  totalDays?: number;
}

export class LeaveType implements ILeaveType {
  constructor(public id?: number, public name?: LeaveTypeName, public totalDays?: number) {}
}
