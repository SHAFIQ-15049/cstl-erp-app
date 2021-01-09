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
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { AttendanceDataUploadService } from 'app/entities/attendance-data-upload/attendance-data-upload.service';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from 'app/entities/employee-salary/employee-salary.service';

type SelectableEntity = IEmployee | IAttendanceDataUpload | IEmployeeSalary;

@Component({
  selector: 'jhi-attendance-update',
  templateUrl: './attendance-update.component.html',
})
export class AttendanceUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  attendancedatauploads: IAttendanceDataUpload[] = [];
  employeesalaries: IEmployeeSalary[] = [];
  attendanceDateDp: any;

  editForm = this.fb.group({
    id: [],
    attendanceDate: [null, [Validators.required]],
    attendanceTime: [null, [Validators.required]],
    considerAs: [null, [Validators.required]],
    employee: [null, Validators.required],
    attendanceDataUpload: [],
    employeeSalary: [],
  });

  constructor(
    protected attendanceService: AttendanceService,
    protected employeeService: EmployeeService,
    protected attendanceDataUploadService: AttendanceDataUploadService,
    protected employeeSalaryService: EmployeeSalaryService,
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

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.attendanceDataUploadService
        .query()
        .subscribe((res: HttpResponse<IAttendanceDataUpload[]>) => (this.attendancedatauploads = res.body || []));

      this.employeeSalaryService.query().subscribe((res: HttpResponse<IEmployeeSalary[]>) => (this.employeesalaries = res.body || []));
    });
  }

  updateForm(attendance: IAttendance): void {
    this.editForm.patchValue({
      id: attendance.id,
      attendanceDate: attendance.attendanceDate,
      attendanceTime: attendance.attendanceTime ? attendance.attendanceTime.format(DATE_TIME_FORMAT) : null,
      considerAs: attendance.considerAs,
      employee: attendance.employee,
      attendanceDataUpload: attendance.attendanceDataUpload,
      employeeSalary: attendance.employeeSalary,
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
      attendanceDate: this.editForm.get(['attendanceDate'])!.value,
      attendanceTime: this.editForm.get(['attendanceTime'])!.value
        ? moment(this.editForm.get(['attendanceTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      considerAs: this.editForm.get(['considerAs'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      attendanceDataUpload: this.editForm.get(['attendanceDataUpload'])!.value,
      employeeSalary: this.editForm.get(['employeeSalary'])!.value,
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
}
