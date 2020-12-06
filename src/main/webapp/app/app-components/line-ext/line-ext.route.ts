import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILine, Line } from 'app/shared/model/line.model';
import { LineExtService } from './line-ext.service';
import { LineExtComponent } from './line-ext.component';
import { LineExtDetailComponent } from './line-ext-detail.component';
import { LineExtUpdateComponent } from './line-ext-update.component';
import {LineService} from "app/entities/line/line.service";
import {LineComponent} from "app/entities/line/line.component";
import {LineDetailComponent} from "app/entities/line/line-detail.component";
import {LineUpdateComponent} from "app/entities/line/line-update.component";

@Injectable({ providedIn: 'root' })
export class LineExtResolve implements Resolve<ILine> {
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

export const lineExtRoute: Routes = [
  {
    path: '',
    component: LineExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LineExtDetailComponent,
    resolve: {
      line: LineExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LineExtUpdateComponent,
    resolve: {
      line: LineExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LineExtUpdateComponent,
    resolve: {
      line: LineExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lines',
    },
    canActivate: [UserRouteAccessService],
  },
];
