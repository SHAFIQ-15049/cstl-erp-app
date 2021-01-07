import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { FineComponent } from './fine.component';
import { FineDetailComponent } from './fine-detail.component';
import { FineUpdateComponent } from './fine-update.component';
import { FineDeleteDialogComponent } from './fine-delete-dialog.component';
import { fineRoute } from './fine.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(fineRoute)],
  declarations: [FineComponent, FineDetailComponent, FineUpdateComponent, FineDeleteDialogComponent],
  entryComponents: [FineDeleteDialogComponent],
})
export class CodeNodeErpFineModule {}
