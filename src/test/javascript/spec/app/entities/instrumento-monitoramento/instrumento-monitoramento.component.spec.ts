/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { InstrumentoMonitoramentoComponent } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento.component';
import { InstrumentoMonitoramentoService } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento.service';
import { InstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

describe('Component Tests', () => {
  describe('InstrumentoMonitoramento Management Component', () => {
    let comp: InstrumentoMonitoramentoComponent;
    let fixture: ComponentFixture<InstrumentoMonitoramentoComponent>;
    let service: InstrumentoMonitoramentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [InstrumentoMonitoramentoComponent],
        providers: []
      })
        .overrideTemplate(InstrumentoMonitoramentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstrumentoMonitoramentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstrumentoMonitoramentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InstrumentoMonitoramento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.instrumentoMonitoramentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
