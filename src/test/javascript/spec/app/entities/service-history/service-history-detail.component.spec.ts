import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { ServiceHistoryDetailComponent } from 'app/entities/service-history/service-history-detail.component';
import { ServiceHistory } from 'app/shared/model/service-history.model';

describe('Component Tests', () => {
  describe('ServiceHistory Management Detail Component', () => {
    let comp: ServiceHistoryDetailComponent;
    let fixture: ComponentFixture<ServiceHistoryDetailComponent>;
    const route = ({ data: of({ serviceHistory: new ServiceHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [ServiceHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ServiceHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
