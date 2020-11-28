import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITraining, Training } from 'app/shared/model/training.model';
import { TrainingService } from './training.service';
import { TrainingComponent } from './training.component';
import { TrainingDetailComponent } from './training-detail.component';
import { TrainingUpdateComponent } from './training-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingResolve implements Resolve<ITraining> {
  constructor(private service: TrainingService, private router: Router) {}

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

export const trainingRoute: Routes = [
  {
    path: '',
    component: TrainingComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainingDetailComponent,
    resolve: {
      training: TrainingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingUpdateComponent,
    resolve: {
      training: TrainingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingUpdateComponent,
    resolve: {
      training: TrainingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Trainings',
    },
    canActivate: [UserRouteAccessService],
  },
];
