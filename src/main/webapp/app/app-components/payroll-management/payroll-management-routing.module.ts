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
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PayrollManagementRoutingModule {}
