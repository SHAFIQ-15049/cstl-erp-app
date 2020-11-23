import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { CodeNodeErpCoreModule } from 'app/core/core.module';
import { CodeNodeErpAppRoutingModule } from './app-routing.module';
import { CodeNodeErpHomeModule } from './home/home.module';
import { CodeNodeErpEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { LoginPageModule } from 'app/login-page/login-page.module';

@NgModule({
  imports: [
    BrowserModule,
    CodeNodeErpSharedModule,
    CodeNodeErpCoreModule,
    CodeNodeErpHomeModule,
    LoginPageModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CodeNodeErpEntityModule,
    CodeNodeErpAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class CodeNodeErpAppModule {}
