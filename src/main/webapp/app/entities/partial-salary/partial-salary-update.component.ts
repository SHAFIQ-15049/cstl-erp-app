import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { merge, Observable, Subject } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPartialSalary, PartialSalary } from 'app/shared/model/partial-salary.model';
import { PartialSalaryService } from './partial-salary.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';

@Component({
  selector: 'jhi-partial-salary-update',
  templateUrl: './partial-salary-update.component.html',
})
export class PartialSalaryUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  fromDateDp: any;
  toDateDp: any;
  employeeSearchStrings: string[] = [];
  employeeSearchStringMapEmployee = new Map();
  selectedEmployee!: string;

  @ViewChild('instance', { static: true }) instance!: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  editForm = this.fb.group({
    id: [],
    year: [null, [Validators.required]],
    month: [null, [Validators.required]],
    totalMonthDays: [null, [Validators.required]],
    fromDate: [null, [Validators.required]],
    toDate: [null, [Validators.required]],
    gross: [],
    basic: [],
    basicPercent: [],
    houseRent: [],
    houseRentPercent: [],
    medicalAllowance: [],
    medicalAllowancePercent: [],
    convinceAllowance: [],
    convinceAllowancePercent: [],
    foodAllowance: [],
    foodAllowancePercent: [],
    fine: [],
    advance: [],
    status: [],
    executedOn: [],
    executedBy: [],
    note: [],
    employee: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected partialSalaryService: PartialSalaryService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partialSalary }) => {
      this.selectedEmployee =
        partialSalary && partialSalary.employee
          ? partialSalary.employee.name +
            '[' +
            partialSalary.employee.localId +
            '] [' +
            partialSalary.employee.department?.name +
            '] [' +
            partialSalary.employee.designation.name +
            ']'
          : '';
      this.updateForm(partialSalary);

      this.employeeService
        .query({
          'status.equals': EmployeeStatus.ACTIVE,
          size: 200000,
        })
        .subscribe((res: HttpResponse<IEmployee[]>) => {
          this.employees = res.body || [];
          this.employees.forEach(e => {
            const searchString = e.name + '[' + e.localId + '] [' + e.department?.name + '] [' + e.designation?.name + ']';
            this.employeeSearchStrings.push(searchString);
            this.employeeSearchStringMapEmployee[searchString] = e;
          });
        });
    });
  }

  updateForm(partialSalary: IPartialSalary): void {
    this.editForm.patchValue({
      id: partialSalary.id,
      year: partialSalary.year,
      month: partialSalary.month,
      totalMonthDays: partialSalary.totalMonthDays,
      fromDate: partialSalary.fromDate ? partialSalary.fromDate.format(DATE_TIME_FORMAT) : null,
      toDate: partialSalary.toDate ? partialSalary.toDate.format(DATE_TIME_FORMAT) : null,
      gross: partialSalary.gross,
      basic: partialSalary.basic,
      basicPercent: partialSalary.basicPercent,
      houseRent: partialSalary.houseRent,
      houseRentPercent: partialSalary.houseRentPercent,
      medicalAllowance: partialSalary.medicalAllowance,
      medicalAllowancePercent: partialSalary.medicalAllowancePercent,
      convinceAllowance: partialSalary.convinceAllowance,
      convinceAllowancePercent: partialSalary.convinceAllowancePercent,
      foodAllowance: partialSalary.foodAllowance,
      foodAllowancePercent: partialSalary.foodAllowancePercent,
      fine: partialSalary.fine,
      advance: partialSalary.advance,
      status: partialSalary.status,
      executedOn: partialSalary.executedOn ? partialSalary.executedOn.format(DATE_TIME_FORMAT) : null,
      executedBy: partialSalary.executedBy,
      note: partialSalary.note,
      employee: partialSalary.employee,
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
    const partialSalary = this.createFromForm();
    partialSalary.employee = this.employeeSearchStringMapEmployee[this.selectedEmployee];
    if (partialSalary.id !== undefined) {
      this.subscribeToSaveResponse(this.partialSalaryService.update(partialSalary));
    } else {
      this.subscribeToSaveResponse(this.partialSalaryService.create(partialSalary));
    }
  }

  private createFromForm(): IPartialSalary {
    return {
      ...new PartialSalary(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      totalMonthDays: this.editForm.get(['totalMonthDays'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      toDate: this.editForm.get(['toDate'])!.value ? moment(this.editForm.get(['toDate'])!.value, DATE_TIME_FORMAT) : undefined,
      gross: this.editForm.get(['gross'])!.value,
      basic: this.editForm.get(['basic'])!.value,
      basicPercent: this.editForm.get(['basicPercent'])!.value,
      houseRent: this.editForm.get(['houseRent'])!.value,
      houseRentPercent: this.editForm.get(['houseRentPercent'])!.value,
      medicalAllowance: this.editForm.get(['medicalAllowance'])!.value,
      medicalAllowancePercent: this.editForm.get(['medicalAllowancePercent'])!.value,
      convinceAllowance: this.editForm.get(['convinceAllowance'])!.value,
      convinceAllowancePercent: this.editForm.get(['convinceAllowancePercent'])!.value,
      foodAllowance: this.editForm.get(['foodAllowance'])!.value,
      foodAllowancePercent: this.editForm.get(['foodAllowancePercent'])!.value,
      fine: this.editForm.get(['fine'])!.value,
      advance: this.editForm.get(['advance'])!.value,
      status: this.editForm.get(['status'])!.value,
      executedOn: this.editForm.get(['executedOn'])!.value ? moment(this.editForm.get(['executedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      executedBy: this.editForm.get(['executedBy'])!.value,
      note: this.editForm.get(['note'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartialSalary>>): void {
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

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term =>
        (term === ''
          ? this.employeeSearchStrings
          : this.employeeSearchStrings.filter(v => v.toLowerCase().includes(term.toLowerCase()))
        ).slice(0, 10)
      )
    );
  };
}
