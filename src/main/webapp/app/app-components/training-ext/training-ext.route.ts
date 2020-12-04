import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingExtService } from './training-ext.service';
import { TrainingExtComponent } from './training-ext.component';
import { TrainingExtDetailComponent } from './training-ext-detail.component';
import { TrainingExtUpdateComponent } from './training-ext-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingExtResolve implements Resolve<ITraining> {
  constructor(private service: TrainingExtService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITraining> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((training: HttpResponse<Training>) => {
          if (training.body) {
            return of(training.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Training());
  }
}

export const trainingExtRoute: Routes = [
  {
    path: '',
    component: TrainingExtComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainingExtDetailComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingExtUpdateComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingExtUpdateComponent,
    resolve: {
      training: TrainingExtResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
];
