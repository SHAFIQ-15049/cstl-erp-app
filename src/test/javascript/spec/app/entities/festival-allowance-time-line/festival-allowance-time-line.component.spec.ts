import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { CodeNodeErpTestModule } from '../../../test.module';
import { FestivalAllowanceTimeLineComponent } from 'app/entities/festival-allowance-time-line/festival-allowance-time-line.component';
import { FestivalAllowanceTimeLineService } from 'app/entities/festival-allowance-time-line/festival-allowance-time-line.service';
import { FestivalAllowanceTimeLine } from 'app/shared/model/festival-allowance-time-line.model';

describe('Component Tests', () => {
  describe('FestivalAllowanceTimeLine Management Component', () => {
    let comp: FestivalAllowanceTimeLineComponent;
    let fixture: ComponentFixture<FestivalAllowanceTimeLineComponent>;
    let service: FestivalAllowanceTimeLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [FestivalAllowanceTimeLineComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(FestivalAllowanceTimeLineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivalAllowanceTimeLineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivalAllowanceTimeLineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FestivalAllowanceTimeLine(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.festivalAllowanceTimeLines && comp.festivalAllowanceTimeLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FestivalAllowanceTimeLine(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.festivalAllowanceTimeLines && comp.festivalAllowanceTimeLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
