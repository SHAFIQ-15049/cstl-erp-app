import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGrade, Grade } from 'app/shared/model/grade.model';
import { GradeService } from './grade.service';
import { GradeComponent } from './grade.component';
import { GradeDetailComponent } from './grade-detail.component';
import { GradeUpdateComponent } from './grade-update.component';

@Injectable({ providedIn: 'root' })
export class GradeResolve implements Resolve<IGrade> {
  constructor(private service: GradeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((grade: HttpResponse<Grade>) => {
          if (grade.body) {
            return of(grade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Grade());
  }
}

export const gradeRoute: Routes = [
  {
    path: '',
    component: GradeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Grades',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GradeDetailComponent,
    resolve: {
      grade: GradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Grades',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GradeUpdateComponent,
    resolve: {
      grade: GradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Grades',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GradeUpdateComponent,
    resolve: {
      grade: GradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Grades',
    },
    canActivate: [UserRouteAccessService],
  },
];
