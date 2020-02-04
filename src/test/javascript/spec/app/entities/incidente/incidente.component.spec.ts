/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { IncidenteComponent } from 'app/entities/incidente/incidente.component';
import { IncidenteService } from 'app/entities/incidente/incidente.service';
import { Incidente } from 'app/shared/model/incidente.model';

describe('Component Tests', () => {
  describe('Incidente Management Component', () => {
    let comp: IncidenteComponent;
    let fixture: ComponentFixture<IncidenteComponent>;
    let service: IncidenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [IncidenteComponent],
        providers: []
      })
        .overrideTemplate(IncidenteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncidenteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidenteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Incidente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.incidentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
