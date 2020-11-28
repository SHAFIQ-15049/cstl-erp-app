import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IThana, Thana } from 'app/shared/model/thana.model';
import { ThanaService } from './thana.service';
import { ThanaComponent } from './thana.component';
import { ThanaDetailComponent } from './thana-detail.component';
import { ThanaUpdateComponent } from './thana-update.component';

@Injectable({ providedIn: 'root' })
export class ThanaResolve implements Resolve<IThana> {
  constructor(private service: ThanaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThana> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((thana: HttpResponse<Thana>) => {
          if (thana.body) {
            return of(thana.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Thana());
  }
}

export const thanaRoute: Routes = [
  {
    path: '',
    component: ThanaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Thanas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThanaDetailComponent,
    resolve: {
      thana: ThanaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Thanas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThanaUpdateComponent,
    resolve: {
      thana: ThanaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Thanas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThanaUpdateComponent,
    resolve: {
      thana: ThanaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Thanas',
    },
    canActivate: [UserRouteAccessService],
  },
];
