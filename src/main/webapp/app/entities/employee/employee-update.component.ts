import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { ILine } from 'app/shared/model/line.model';
import { LineService } from 'app/entities/line/line.service';

type SelectableEntity = ICompany | IDepartment | IGrade | IDesignation | ILine;

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  departments: IDepartment[] = [];
  grades: IGrade[] = [];
  designations: IDesignation[] = [];
  lines: ILine[] = [];
  joiningDateDp: any;
  terminationDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    empId: [null, [Validators.required]],
    globalId: [null, [Validators.required]],
    attendanceMachineId: [],
    localId: [null, [Validators.required]],
    category: [],
    type: [],
    joiningDate: [],
    status: [],
    terminationDate: [],
    terminationReason: [],
    company: [],
    department: [],
    grade: [],
    designation: [],
    line: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeService: EmployeeService,
    protected companyService: CompanyService,
    protected departmentService: DepartmentService,
    protected gradeService: GradeService,
    protected designationService: DesignationService,
    protected lineService: LineService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

      this.lineService.query().subscribe((res: HttpResponse<ILine[]>) => (this.lines = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      name: employee.name,
      empId: employee.empId,
      globalId: employee.globalId,
      attendanceMachineId: employee.attendanceMachineId,
      localId: employee.localId,
      category: employee.category,
      type: employee.type,
      joiningDate: employee.joiningDate,
      status: employee.status,
      terminationDate: employee.terminationDate,
      terminationReason: employee.terminationReason,
      company: employee.company,
      department: employee.department,
      grade: employee.grade,
      designation: employee.designation,
      line: employee.line,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('codeNodeErpApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      empId: this.editForm.get(['empId'])!.value,
      globalId: this.editForm.get(['globalId'])!.value,
      attendanceMachineId: this.editForm.get(['attendanceMachineId'])!.value,
      localId: this.editForm.get(['localId'])!.value,
      category: this.editForm.get(['category'])!.value,
      type: this.editForm.get(['type'])!.value,
      joiningDate: this.editForm.get(['joiningDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      terminationDate: this.editForm.get(['terminationDate'])!.value,
      terminationReason: this.editForm.get(['terminationReason'])!.value,
      company: this.editForm.get(['company'])!.value,
      department: this.editForm.get(['department'])!.value,
      grade: this.editForm.get(['grade'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      line: this.editForm.get(['line'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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
