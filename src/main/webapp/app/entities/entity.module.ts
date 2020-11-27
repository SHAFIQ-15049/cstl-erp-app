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
        loadChildren: () => import('./employee/employee.module').then(m => m.CodeNodeErpEmployeeModule),
      },
      {
        path: 'personal-info',
        loadChildren: () => import('./personal-info/personal-info.module').then(m => m.CodeNodeErpPersonalInfoModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.CodeNodeErpAddressModule),
      },
      {
        path: 'educational-info',
        loadChildren: () => import('./educational-info/educational-info.module').then(m => m.CodeNodeErpEducationalInfoModule),
      },
      {
        path: 'training',
        loadChildren: () => import('./training/training.module').then(m => m.CodeNodeErpTrainingModule),
      },
      {
        path: 'employee-account',
        loadChildren: () => import('./employee-account/employee-account.module').then(m => m.CodeNodeErpEmployeeAccountModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.CodeNodeErpJobHistoryModule),
      },
      {
        path: 'service-history',
        loadChildren: () => import('./service-history/service-history.module').then(m => m.CodeNodeErpServiceHistoryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CodeNodeErpEntityModule {}
