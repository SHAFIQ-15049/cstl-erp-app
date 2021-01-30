import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { ILine } from 'app/shared/model/line.model';
import { IGrade } from 'app/shared/model/grade.model';
import { AttendanceMarkedAs } from 'app/shared/model/enumerations/attendance-marked-as.model';
import { LeaveAppliedStatus } from 'app/shared/model/enumerations/leave-applied-status.model';
import { EmployeeCategory } from 'app/shared/model/enumerations/employee-category.model';
import { EmployeeType } from 'app/shared/model/enumerations/employee-type.model';

export interface IAttendance {
  id?: number;
  attendanceTime?: Moment;
  machineNo?: string;
  markedAs?: AttendanceMarkedAs;
  leaveApplied?: LeaveAppliedStatus;
  employeeMachineId?: string;
  employeeCategory?: EmployeeCategory;
  employeeType?: EmployeeType;
  employee?: IEmployee;
  employeeSalary?: IEmployeeSalary;
  department?: IDepartment;
  designation?: IDesignation;
  line?: ILine;
  grade?: IGrade;
}

export class Attendance implements IAttendance {
  constructor(
    public id?: number,
    public attendanceTime?: Moment,
    public machineNo?: string,
    public markedAs?: AttendanceMarkedAs,
    public leaveApplied?: LeaveAppliedStatus,
    public employeeMachineId?: string,
    public employeeCategory?: EmployeeCategory,
    public employeeType?: EmployeeType,
    public employee?: IEmployee,
    public employeeSalary?: IEmployeeSalary,
    public department?: IDepartment,
    public designation?: IDesignation,
    public line?: ILine,
    public grade?: IGrade
  ) {}
}
