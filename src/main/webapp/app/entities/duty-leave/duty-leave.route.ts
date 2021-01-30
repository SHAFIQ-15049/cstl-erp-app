import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DutyLeave, IDutyLeave } from 'app/shared/model/duty-leave.model';
import { DutyLeaveService } from './duty-leave.service';
import { DutyLeaveComponent } from './duty-leave.component';
import { DutyLeaveDetailComponent } from './duty-leave-detail.component';

@Injectable({ providedIn: 'root' })
export class DutyLeaveResolve implements Resolve<IDutyLeave> {
  constructor(private service: DutyLeaveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDutyLeave> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dutyLeave: HttpResponse<DutyLeave>) => {
          if (dutyLeave.body) {
            return of(dutyLeave.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DutyLeave());
  }
}

export const dutyLeaveRoute: Routes = [
  {
    path: '',
    component: DutyLeaveComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DutyLeaves',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DutyLeaveDetailComponent,
    resolve: {
      dutyLeave: DutyLeaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DutyLeaves',
    },
    canActivate: [UserRouteAccessService],
  },
];
