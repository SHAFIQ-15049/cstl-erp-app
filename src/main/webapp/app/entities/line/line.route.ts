import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILine, Line } from 'app/shared/model/line.model';
import { LineService } from './line.service';
import { LineComponent } from './line.component';
import { LineDetailComponent } from './line-detail.component';
import { LineUpdateComponent } from './line-update.component';

@Injectable({ providedIn: 'root' })
export class LineResolve implements Resolve<ILine> {
  constructor(private service: LineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((line: HttpResponse<Line>) => {
          if (line.body) {
            return of(line.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Line());
  }
}

export const lineRoute: Routes = [
  {
    path: '',
    component: LineComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LineDetailComponent,
    resolve: {
      line: LineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LineUpdateComponent,
    resolve: {
      line: LineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LineUpdateComponent,
    resolve: {
      line: LineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
];
