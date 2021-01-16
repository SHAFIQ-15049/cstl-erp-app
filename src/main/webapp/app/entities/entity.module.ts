import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.CodeNodeErpCompanyModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.CodeNodeErpDepartmentModule),
      },
      {
        path: 'designation',
        loadChildren: () => import('./designation/designation.module').then(m => m.CodeNodeErpDesignationModule),
      },
      {
        path: 'grade',
        loadChildren: () => import('./grade/grade.module').then(m => m.CodeNodeErpGradeModule),
      },
      {
        path: 'division',
        loadChildren: () => import('./division/division.module').then(m => m.CodeNodeErpDivisionModule),
      },
      {
        path: 'district',
        loadChildren: () => import('./district/district.module').then(m => m.CodeNodeErpDistrictModule),
      },
      {
        path: 'thana',
        loadChildren: () => import('./thana/thana.module').then(m => m.CodeNodeErpThanaModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('../app-components/employee-ext/employee-ext.module').then(m => m.CodeNodeErpEmployeeModule),
      },
      {
        path: 'personal-info',
        loadChildren: () =>
          import('../app-components/personal-info-ext/personal-info-ext.module').then(m => m.CodeNodeErpPersonalInfoModule),
      },
      {
        path: 'address',
        loadChildren: () => import('../app-components/address-ext/address-ext.module').then(m => m.CodeNodeErpAddressModule),
      },
      {
        path: 'educational-info',
        loadChildren: () =>
          import('../app-components/educational-info-ext/educational-info-ext.module').then(m => m.CodeNodeErpEducationalInfoModule),
      },
      {
        path: 'training',
        loadChildren: () => import('../app-components/training-ext/training-ext.module').then(m => m.CodeNodeErpTrainingModule),
      },
      {
        path: 'employee-account',
        loadChildren: () =>
          import('../app-components/employee-account-ext/employee-account-ext.module').then(m => m.CodeNodeErpEmployeeAccountModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('../app-components/job-history-ext/job-history-ext.module').then(m => m.CodeNodeErpJobHistoryModule),
      },
      {
        path: 'service-history',
        loadChildren: () =>
          import('../app-components/service-history-ext/service-history-ext.module').then(m => m.CodeNodeErpServiceHistoryModule),
      },
      {
        path: 'line',
        loadChildren: () => import('../app-components/line-ext/line-ext.module').then(m => m.CodeNodeErpLineModule),
      },
      {
        path: 'weekend',
        loadChildren: () => import('./weekend/weekend.module').then(m => m.CodeNodeErpWeekendModule),
      },
      {
        path: 'holiday-type',
        loadChildren: () => import('./holiday-type/holiday-type.module').then(m => m.CodeNodeErpHolidayTypeModule),
      },
      {
        path: 'holiday',
        loadChildren: () => import('./holiday/holiday.module').then(m => m.CodeNodeErpHolidayModule),
      },
      {
        path: 'leave-type',
        loadChildren: () => import('./leave-type/leave-type.module').then(m => m.CodeNodeErpLeaveTypeModule),
      },
      {
        path: 'leave-application',
        loadChildren: () => import('./leave-application/leave-application.module').then(m => m.CodeNodeErpLeaveApplicationModule),
      },
      {
        path: 'attendance-data-upload',
        loadChildren: () =>
          import('./attendance-data-upload/attendance-data-upload.module').then(m => m.CodeNodeErpAttendanceDataUploadModule),
      },
      {
        path: 'fine',
        loadChildren: () => import('./fine/fine.module').then(m => m.CodeNodeErpFineModule),
      },
      {
        path: 'fine-payment-history',
        loadChildren: () => import('./fine-payment-history/fine-payment-history.module').then(m => m.CodeNodeErpFinePaymentHistoryModule),
      },
      {
        path: 'advance',
        loadChildren: () => import('./advance/advance.module').then(m => m.CodeNodeErpAdvanceModule),
      },
      {
        path: 'advance-payment-history',
        loadChildren: () =>
          import('./advance-payment-history/advance-payment-history.module').then(m => m.CodeNodeErpAdvancePaymentHistoryModule),
      },
      {
        path: 'festival-allowance-time-line',
        loadChildren: () =>
          import('./festival-allowance-time-line/festival-allowance-time-line.module').then(
            m => m.CodeNodeErpFestivalAllowanceTimeLineModule
          ),
      },
      {
        path: 'default-allowance',
        loadChildren: () => import('./default-allowance/default-allowance.module').then(m => m.CodeNodeErpDefaultAllowanceModule),
      },
      {
        path: 'employee-salary',
        loadChildren: () => import('./employee-salary/employee-salary.module').then(m => m.CodeNodeErpEmployeeSalaryModule),
      },
      {
        path: 'monthly-salary',
        loadChildren: () => import('./monthly-salary/monthly-salary.module').then(m => m.CodeNodeErpMonthlySalaryModule),
      },
      {
        path: 'monthly-salary-dtl',
        loadChildren: () => import('./monthly-salary-dtl/monthly-salary-dtl.module').then(m => m.CodeNodeErpMonthlySalaryDtlModule),
      },
      {
        path: 'payroll-management',
        loadChildren: () => import('../app-components/payroll-management/payroll-management.module').then(m => m.PayrollManagementModule),
      },
      {
        path: 'festival-allowance-payment',
        loadChildren: () =>
          import('./festival-allowance-payment/festival-allowance-payment.module').then(m => m.CodeNodeErpFestivalAllowancePaymentModule),
      },
      {
        path: 'festival-allowance-payment-dtl',
        loadChildren: () =>
          import('./festival-allowance-payment-dtl/festival-allowance-payment-dtl.module').then(
            m => m.CodeNodeErpFestivalAllowancePaymentDtlModule
          ),
      },
      {
        path: 'attendance',
        loadChildren: () => import('./attendance/attendance.module').then(m => m.CodeNodeErpAttendanceModule),
      },
      {
        path: 'partial-salary',
        loadChildren: () => import('./partial-salary/partial-salary.module').then(m => m.CodeNodeErpPartialSalaryModule),
      },
      {
        path: 'attendance-summary',
        loadChildren: () => import('./attendance-summary/attendance-summary.module').then(m => m.CodeNodeErpAttendanceSummaryModule),
      },
      {
        path: 'leave-balance',
        loadChildren: () => import('./leave-balance/leave-balance.module').then(m => m.CodeNodeErpLeaveBalanceModule),
      },
      {
        path: 'over-time',
        loadChildren: () => import('./over-time/over-time.module').then(m => m.CodeNodeErpOverTimeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CodeNodeErpEntityModule {}
