import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { AttendanceMarkedAs } from 'app/shared/model/enumerations/attendance-marked-as.model';

export interface IAttendance {
  id?: number;
  attendanceTime?: Moment;
  machineNo?: string;
  markedAs?: AttendanceMarkedAs;
  employee?: IEmployee;
  attendanceDataUpload?: IAttendanceDataUpload;
  employeeSalary?: IEmployeeSalary;
}

export class Attendance implements IAttendance {
  constructor(
    public id?: number,
    public attendanceTime?: Moment,
    public machineNo?: string,
    public markedAs?: AttendanceMarkedAs,
    public employee?: IEmployee,
    public attendanceDataUpload?: IAttendanceDataUpload,
    public employeeSalary?: IEmployeeSalary
  ) {}
}
