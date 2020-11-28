import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { CompanyComponent } from 'app/entities/company/company.component';
import { CompanyUpdateComponent } from 'app/entities/company/company-update.component';
import { companyRoute } from 'app/entities/company/company.route';
import { CompanyDetailComponent } from 'app/entities/company/company-detail.component';
import { CompanyDeleteDialogComponent } from 'app/entities/company/company-delete-dialog.component';
import { CompanyExtendedComponent } from 'app/app-components/company-extended/company-extended.component';
import { CompanyExtendedUpdateComponent } from 'app/app-components/company-extended/company-extended-update.component';
import { companyExtendedRoute } from 'app/app-components/company-extended/company-extended.route';
import { CompanyExtendedDetailComponent } from 'app/app-components/company-extended/company-extended-detail.component';

@NgModule({
  /*  imports: [CodeNodeErpSharedModule, RouterModule.forChild(companyExtendedRoute)],
  declarations: [
    CompanyComponent,
    CompanyExtendedComponent,
    CompanyDetailComponent,
    CompanyExtendedDetailComponent,
    CompanyUpdateComponent,
    CompanyExtendedUpdateComponent,
    CompanyDeleteDialogComponent,
  ],
  entryComponents: [CompanyDeleteDialogComponent],*/
})
export class CodeNodeErpCompanyModule {}
