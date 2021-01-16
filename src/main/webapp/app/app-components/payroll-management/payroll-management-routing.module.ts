import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PayrollManagementComponent } from 'app/app-components/payroll-management/payroll-management.component';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

const routes: Routes = [
  {
    path: '',
    component: PayrollManagementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Payroll Management',
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: 'monthly-salary-dtl',
        loadChildren: () =>
          import('../../entities/monthly-salary-dtl/monthly-salary-dtl.module').then(m => m.CodeNodeErpMonthlySalaryDtlModule),
      },
    ],
  },
  {
    path: ':selectedYear/:selectedMonth/:selectedDesignationId/:fromDate/:toDate',
    component: PayrollManagementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Payroll Management',
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: 'monthly-salary-dtl',
        loadChildren: () =>
          import('../../entities/monthly-salary-dtl/monthly-salary-dtl.module').then(m => m.CodeNodeErpMonthlySalaryDtlModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PayrollManagementRoutingModule {}
