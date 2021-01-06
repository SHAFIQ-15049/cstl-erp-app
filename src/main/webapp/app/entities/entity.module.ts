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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CodeNodeErpEntityModule {}
