import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CodeNodeErpTestModule } from '../../../test.module';
import { LineComponent } from 'app/entities/line/line.component';
import { LineService } from 'app/entities/line/line.service';
import { Line } from 'app/shared/model/line.model';

describe('Component Tests', () => {
  describe('Line Management Component', () => {
    let comp: LineComponent;
    let fixture: ComponentFixture<LineComponent>;
    let service: LineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [LineComponent],
      })
        .overrideTemplate(LineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Line(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lines && comp.lines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
