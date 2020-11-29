import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from 'app/shared/constants/authority.constants';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'Welcome to CSTL!',
  },
  canActivate: [UserRouteAccessService],
};
