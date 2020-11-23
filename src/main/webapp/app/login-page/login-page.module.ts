import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from 'app/login-page/login-page.component';
import { RouterModule } from '@angular/router';
import { LOGIN_PAGE_ROUTE } from 'app/login-page/login-page.route';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { NgbModalModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [LoginPageComponent],
  imports: [CodeNodeErpSharedModule, CommonModule, RouterModule.forChild([LOGIN_PAGE_ROUTE])],
})
export class LoginPageModule {}
