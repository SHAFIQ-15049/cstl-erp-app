import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServiceHistory, ServiceHistory } from 'app/shared/model/service-history.model';
import { ServiceHistoryService } from './service-history.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IDepartment | IDesignation | IGrade | IEmployee;

@Component({
  selector: 'jhi-service-history-update',
  templateUrl: './service-history-update.component.html',
})
export class ServiceHistoryUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];
  designations: IDesignation[] = [];
  grades: IGrade[] = [];
  employees: IEmployee[] = [];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    employeeType: [],
    from: [],
    to: [],
    department: [],
    designation: [],
    grade: [],
    employee: [],
  });

  constructor(
    protected serviceHistoryService: ServiceHistoryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected gradeService: GradeService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceHistory }) => {
      this.updateForm(serviceHistory);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(serviceHistory: IServiceHistory): void {
    this.editForm.patchValue({
      id: serviceHistory.id,
      employeeType: serviceHistory.employeeType,
      from: serviceHistory.from,
      to: serviceHistory.to,
      department: serviceHistory.department,
      designation: serviceHistory.designation,
      grade: serviceHistory.grade,
      employee: serviceHistory.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceHistory = this.createFromForm();
    if (serviceHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceHistoryService.update(serviceHistory));
    } else {
      this.subscribeToSaveResponse(this.serviceHistoryService.create(serviceHistory));
    }
  }

  private createFromForm(): IServiceHistory {
    return {
      ...new ServiceHistory(),
      id: this.editForm.get(['id'])!.value,
      employeeType: this.editForm.get(['employeeType'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      department: this.editForm.get(['department'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      grade: this.editForm.get(['grade'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceHistory>>): void {
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
