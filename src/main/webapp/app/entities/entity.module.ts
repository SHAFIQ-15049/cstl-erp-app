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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CodeNodeErpEntityModule {}
