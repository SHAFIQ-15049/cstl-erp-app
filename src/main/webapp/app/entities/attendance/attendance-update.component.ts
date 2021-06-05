import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { Attendance, IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from 'app/entities/employee-salary/employee-salary.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { ILine } from 'app/shared/model/line.model';
import { LineService } from 'app/entities/line/line.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';

type SelectableEntity = IEmployee | IEmployeeSalary | IDepartment | IDesignation | ILine | IGrade;

@Component({
  selector: 'jhi-attendance-update',
  templateUrl: './attendance-update.component.html',
})
export class AttendanceUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  employeesalaries: IEmployeeSalary[] = [];
  departments: IDepartment[] = [];
  designations: IDesignation[] = [];
  lines: ILine[] = [];
  grades: IGrade[] = [];
  empId?: string;

  editForm = this.fb.group({
    id: [],
    attendanceTime: [null, [Validators.required]],
    machineNo: [null, [Validators.required]],
    markedAs: [],
    leaveApplied: [],
    employeeMachineId: [],
    employeeCategory: [],
    employeeType: [],
    employee: [null, Validators.required],
    employeeSalary: [],
    department: [],
    designation: [],
    line: [],
    grade: [],
  });

  constructor(
    protected attendanceService: AttendanceService,
    protected employeeService: EmployeeService,
    protected employeeSalaryService: EmployeeSalaryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected lineService: LineService,
    protected gradeService: GradeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendance }) => {
      if (!attendance.id) {
        const today = moment().startOf('day');
        attendance.attendanceTime = today;
      }

      this.updateForm(attendance);

      this.employeeService.query({ size: 10000 }).subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.employeeSalaryService
        .query({ size: 10000 })
        .subscribe((res: HttpResponse<IEmployeeSalary[]>) => (this.employeesalaries = res.body || []));

      this.departmentService.query({ size: 10000 }).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.designationService.query({ size: 10000 }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

      this.lineService.query({ size: 10000 }).subscribe((res: HttpResponse<ILine[]>) => (this.lines = res.body || []));

      this.gradeService.query({ size: 10000 }).subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
    });
  }

  updateForm(attendance: IAttendance): void {
    this.editForm.patchValue({
      id: attendance.id,
      attendanceTime: attendance.attendanceTime ? attendance.attendanceTime.format(DATE_TIME_FORMAT) : null,
      machineNo: attendance.machineNo,
      markedAs: attendance.markedAs,
      leaveApplied: attendance.leaveApplied,
      employeeMachineId: attendance.employeeMachineId,
      employeeCategory: attendance.employeeCategory,
      employeeType: attendance.employeeType,
      employee: attendance.employee,
      employeeSalary: attendance.employeeSalary,
      department: attendance.department,
      designation: attendance.designation,
      line: attendance.line,
      grade: attendance.grade,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendance = this.createFromForm();
    if (attendance.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceService.update(attendance));
    } else {
      this.subscribeToSaveResponse(this.attendanceService.create(attendance));
    }
  }

  private createFromForm(): IAttendance {
    return {
      ...new Attendance(),
      id: this.editForm.get(['id'])!.value,
      attendanceTime: this.editForm.get(['attendanceTime'])!.value
        ? moment(this.editForm.get(['attendanceTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      machineNo: this.editForm.get(['machineNo'])!.value,
      markedAs: this.editForm.get(['markedAs'])!.value,
      leaveApplied: this.editForm.get(['leaveApplied'])!.value,
      employeeMachineId: this.editForm.get(['employeeMachineId'])!.value,
      employeeCategory: this.editForm.get(['employeeCategory'])!.value,
      employeeType: this.editForm.get(['employeeType'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      employeeSalary: this.editForm.get(['employeeSalary'])!.value,
      department: this.editForm.get(['department'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      line: this.editForm.get(['line'])!.value,
      grade: this.editForm.get(['grade'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  searchByEmpId(): void {
    for (let i = 0; i < this.employees.length; i++) {
      if (this.empId === this.employees[i].empId) {
        this.editForm.patchValue({
          employee: this.employees[i],
        });
        break;
      }
    }
  }
}
