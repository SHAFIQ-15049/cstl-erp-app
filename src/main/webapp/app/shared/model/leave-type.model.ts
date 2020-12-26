export interface ILeaveType {
  id?: number;
  name?: string;
  totalDays?: number;
}

export class LeaveType implements ILeaveType {
  constructor(public id?: number, public name?: string, public totalDays?: number) {}
}
