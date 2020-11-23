import { Route } from '@angular/router';
import { LoginPageComponent } from 'app/login-page/login-page.component';

export const LOGIN_PAGE_ROUTE: Route = {
  path: 'login',
  component: LoginPageComponent,
  data: {
    authorities: [],
    pageTitle: 'Please Login',
  },
};
