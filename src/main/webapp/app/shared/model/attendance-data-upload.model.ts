export interface IAttendanceDataUpload {
  id?: number;
  attendanceData?: any;
}

export class AttendanceDataUpload implements IAttendanceDataUpload {
  constructor(public id?: number, public attendanceData?: any) {}
}
