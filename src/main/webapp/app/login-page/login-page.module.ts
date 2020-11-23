import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from 'app/login-page/login-page.component';
import { RouterModule } from '@angular/router';
import { LOGIN_PAGE_ROUTE } from 'app/login-page/login-page.route';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [LoginPageComponent],
  imports: [CommonModule, ReactiveFormsModule, RouterModule.forChild([LOGIN_PAGE_ROUTE])],
})
export class LoginPageModule {}
