import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CodeNodeErpTestModule } from '../../../test.module';
import { OverTimeComponent } from 'app/entities/over-time/over-time.component';
import { OverTimeService } from 'app/entities/over-time/over-time.service';
import { OverTime } from 'app/shared/model/over-time.model';

describe('Component Tests', () => {
  describe('OverTime Management Component', () => {
    let comp: OverTimeComponent;
    let fixture: ComponentFixture<OverTimeComponent>;
    let service: OverTimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [OverTimeComponent],
      })
        .overrideTemplate(OverTimeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OverTimeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OverTimeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OverTime(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.overTimes && comp.overTimes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
