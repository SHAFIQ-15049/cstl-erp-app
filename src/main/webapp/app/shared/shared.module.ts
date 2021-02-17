import { NgModule } from '@angular/core';
import { CodeNodeErpSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { DivisionFilterPipe } from './pipes/division-filter.pipe';
import { DistrictFilterPipe } from './pipes/district-filter.pipe';
import { GradeFilterPipe } from './pipes/grade-filter.pipe';
import { DesignationFilterPipe } from 'app/shared/pipes/designation-filter.pipe';

@NgModule({
  imports: [CodeNodeErpSharedLibsModule],
  declarations: [
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    DivisionFilterPipe,
    DistrictFilterPipe,
    GradeFilterPipe,
    DesignationFilterPipe,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    CodeNodeErpSharedLibsModule,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    DivisionFilterPipe,
    DistrictFilterPipe,
    GradeFilterPipe,
    DesignationFilterPipe,
  ],
})
export class CodeNodeErpSharedModule {}
