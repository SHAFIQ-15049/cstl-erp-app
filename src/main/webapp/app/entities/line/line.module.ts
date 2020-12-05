import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { LineComponent } from './line.component';
import { LineDetailComponent } from './line-detail.component';
import { LineUpdateComponent } from './line-update.component';
import { LineDeleteDialogComponent } from './line-delete-dialog.component';
import { lineRoute } from './line.route';

@NgModule({
  // imports: [CodeNodeErpSharedModule, RouterModule.forChild(lineRoute)],
  // declarations: [LineComponent, LineDetailComponent, LineUpdateComponent, LineDeleteDialogComponent],
  // entryComponents: [LineDeleteDialogComponent],
})
export class CodeNodeErpLineModule {}
