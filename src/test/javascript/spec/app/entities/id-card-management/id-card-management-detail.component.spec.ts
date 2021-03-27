import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { IdCardManagementDetailComponent } from 'app/entities/id-card-management/id-card-management-detail.component';
import { IdCardManagement } from 'app/shared/model/id-card-management.model';

describe('Component Tests', () => {
  describe('IdCardManagement Management Detail Component', () => {
    let comp: IdCardManagementDetailComponent;
    let fixture: ComponentFixture<IdCardManagementDetailComponent>;
    const route = ({ data: of({ idCardManagement: new IdCardManagement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [IdCardManagementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IdCardManagementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdCardManagementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load idCardManagement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.idCardManagement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
