import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPersonalInfo } from 'app/shared/model/personal-info.model';
import { PersonalInfoService } from 'app/entities/personal-info/personal-info.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';

type SelectableEntity = IPersonalInfo | ICompany | IDepartment | IGrade | IDesignation;

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  personalinfos: IPersonalInfo[] = [];
  companies: ICompany[] = [];
  departments: IDepartment[] = [];
  grades: IGrade[] = [];
  designations: IDesignation[] = [];
  joiningDateDp: any;
  terminationDateDp: any;

  editForm = this.fb.group({
    id: [],
    employeeId: [null, [Validators.required]],
    globalId: [null, [Validators.required]],
    localId: [null, [Validators.required]],
    type: [],
    joiningDate: [],
    terminationDate: [],
    terminationReason: [],
    personalInfo: [],
    company: [],
    department: [],
    grade: [],
    designation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeeService: EmployeeService,
    protected personalInfoService: PersonalInfoService,
    protected companyService: CompanyService,
    protected departmentService: DepartmentService,
    protected gradeService: GradeService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.personalInfoService
        .query({ 'employeeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IPersonalInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPersonalInfo[]) => {
          if (!employee.personalInfo || !employee.personalInfo.id) {
            this.personalinfos = resBody;
          } else {
            this.personalInfoService
              .find(employee.personalInfo.id)
              .pipe(
                map((subRes: HttpResponse<IPersonalInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPersonalInfo[]) => (this.personalinfos = concatRes));
          }
        });

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      employeeId: employee.employeeId,
      globalId: employee.globalId,
      localId: employee.localId,
      type: employee.type,
      joiningDate: employee.joiningDate,
      terminationDate: employee.terminationDate,
      terminationReason: employee.terminationReason,
      personalInfo: employee.personalInfo,
      company: employee.company,
      department: employee.department,
      grade: employee.grade,
      designation: employee.designation,
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
      employeeId: this.editForm.get(['employeeId'])!.value,
      globalId: this.editForm.get(['globalId'])!.value,
      localId: this.editForm.get(['localId'])!.value,
      type: this.editForm.get(['type'])!.value,
      joiningDate: this.editForm.get(['joiningDate'])!.value,
      terminationDate: this.editForm.get(['terminationDate'])!.value,
      terminationReason: this.editForm.get(['terminationReason'])!.value,
      personalInfo: this.editForm.get(['personalInfo'])!.value,
      company: this.editForm.get(['company'])!.value,
      department: this.editForm.get(['department'])!.value,
      grade: this.editForm.get(['grade'])!.value,
      designation: this.editForm.get(['designation'])!.value,
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
