import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { ConsiderAsType } from 'app/shared/model/enumerations/consider-as-type.model';

export interface IAttendance {
  id?: number;
  attendanceDate?: Moment;
  attendanceTime?: Moment;
  considerAs?: ConsiderAsType;
  employee?: IEmployee;
  attendanceDataUpload?: IAttendanceDataUpload;
  employeeSalary?: IEmployeeSalary;
}

export class Attendance implements IAttendance {
  constructor(
    public id?: number,
    public attendanceDate?: Moment,
    public attendanceTime?: Moment,
    public considerAs?: ConsiderAsType,
    public employee?: IEmployee,
    public attendanceDataUpload?: IAttendanceDataUpload,
    public employeeSalary?: IEmployeeSalary
  ) {}
}
