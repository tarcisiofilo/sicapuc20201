/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NivelIncidenteComponent } from 'app/entities/nivel-incidente/nivel-incidente.component';
import { NivelIncidenteService } from 'app/entities/nivel-incidente/nivel-incidente.service';
import { NivelIncidente } from 'app/shared/model/nivel-incidente.model';

describe('Component Tests', () => {
  describe('NivelIncidente Management Component', () => {
    let comp: NivelIncidenteComponent;
    let fixture: ComponentFixture<NivelIncidenteComponent>;
    let service: NivelIncidenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NivelIncidenteComponent],
        providers: []
      })
        .overrideTemplate(NivelIncidenteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelIncidenteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelIncidenteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NivelIncidente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nivelIncidentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
