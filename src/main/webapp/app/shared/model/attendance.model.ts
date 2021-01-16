import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';

export interface IAttendance {
  id?: number;
  attendanceTime?: Moment;
  machineNo?: string;
  employee?: IEmployee;
  attendanceDataUpload?: IAttendanceDataUpload;
  employeeSalary?: IEmployeeSalary;
}

export class Attendance implements IAttendance {
  constructor(
    public id?: number,
    public attendanceTime?: Moment,
    public machineNo?: string,
    public employee?: IEmployee,
    public attendanceDataUpload?: IAttendanceDataUpload,
    public employeeSalary?: IEmployeeSalary
  ) {}
}
