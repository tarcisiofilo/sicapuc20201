/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { AreaSusceptivelComponent } from 'app/entities/area-susceptivel/area-susceptivel.component';
import { AreaSusceptivelService } from 'app/entities/area-susceptivel/area-susceptivel.service';
import { AreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

describe('Component Tests', () => {
  describe('AreaSusceptivel Management Component', () => {
    let comp: AreaSusceptivelComponent;
    let fixture: ComponentFixture<AreaSusceptivelComponent>;
    let service: AreaSusceptivelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [AreaSusceptivelComponent],
        providers: []
      })
        .overrideTemplate(AreaSusceptivelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AreaSusceptivelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AreaSusceptivelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AreaSusceptivel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.areaSusceptivels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
