import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/leave-type.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { LeaveApplicationStatus } from 'app/shared/model/enumerations/leave-application-status.model';
import { LeaveBalanceService } from 'app/entities/leave-balance/leave-balance.service';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';

type SelectableEntity = IUser | ILeaveType | IEmployee;

@Component({
  selector: 'jhi-leave-application-update',
  templateUrl: './leave-application-update.component.html',
})
export class LeaveApplicationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  leavetypes: ILeaveType[] = [];
  employees: IEmployee[] = [];
  fromDp: any;
  toDp: any;

  currentUser: Account | null = null;
  leaveBalances: ILeaveBalance[] = [];

  editForm = this.fb.group({
    id: [],
    from: [null, [Validators.required]],
    to: [null, [Validators.required]],
    totalDays: [null, [Validators.required]],
    status: [null, [Validators.required]],
    reason: [null, [Validators.required]],
    appliedBy: [null, Validators.required],
    actionTakenBy: [],
    leaveType: [null, Validators.required],
    applicant: [null, Validators.required],
  });

  constructor(
    protected leaveApplicationService: LeaveApplicationService,
    protected leaveBalanceService: LeaveBalanceService,
    protected userService: UserService,
    protected leaveTypeService: LeaveTypeService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(currentUser => {
      this.currentUser = currentUser;
    });

    this.activatedRoute.data.subscribe(({ leaveApplication }) => {
      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.leaveTypeService.query().subscribe((res: HttpResponse<ILeaveType[]>) => (this.leavetypes = res.body || []));

      this.employeeService
        .query({
          'localId.equals': this.currentUser?.login,
        })
        .subscribe((res: HttpResponse<IEmployee[]>) => {
          this.employees = res.body || [];

          this.updateForm(leaveApplication);

          if (this.employees) {
            this.leaveBalanceService.findByEmployeeId(this.employees[0].id!).subscribe((res1: HttpResponse<ILeaveBalance[]>) => {
              this.leaveBalances = res1.body!;
            });
          }
        });
    });

    this.onChanges();
  }

  updateForm(leaveApplication: ILeaveApplication): void {
    this.editForm.patchValue({
      id: leaveApplication.id,
      from: leaveApplication.from,
      to: leaveApplication.to,
      totalDays: leaveApplication.totalDays,
      status: leaveApplication.status ? leaveApplication.status : LeaveApplicationStatus.APPLIED,
      reason: leaveApplication.reason,
      appliedBy: this.currentUser,
      actionTakenBy: leaveApplication.actionTakenBy,
      leaveType: leaveApplication.leaveType,
      applicant: leaveApplication.applicant ? leaveApplication.applicant : this.employees[0],
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leaveApplication = this.createFromForm();
    if (leaveApplication.id !== undefined) {
      this.subscribeToSaveResponse(this.leaveApplicationService.update(leaveApplication));
    } else {
      this.subscribeToSaveResponse(this.leaveApplicationService.create(leaveApplication));
    }
  }

  private createFromForm(): ILeaveApplication {
    return {
      ...new LeaveApplication(),
      id: this.editForm.get(['id'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      totalDays: this.editForm.get(['totalDays'])!.value,
      status: this.editForm.get(['status'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      appliedBy: this.editForm.get(['appliedBy'])!.value,
      actionTakenBy: this.editForm.get(['actionTakenBy'])!.value,
      leaveType: this.editForm.get(['leaveType'])!.value,
      applicant: this.editForm.get(['applicant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveApplication>>): void {
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

  onChanges(): void {
    this.editForm.get('from')!.valueChanges.subscribe(() => {
      this.updateTotalDays();
    });

    this.editForm.get('to')!.valueChanges.subscribe(() => {
      this.updateTotalDays();
    });
  }

  private updateTotalDays(): void {
    const fromDate = new Date(this.editForm.get('from')!.value);
    const toDate = new Date(this.editForm.get('to')!.value);
    const diff = toDate.getTime() - fromDate.getTime();
    this.editForm.patchValue({
      totalDays: diff / (1000 * 60 * 60 * 24) + 1,
    });
  }
}
