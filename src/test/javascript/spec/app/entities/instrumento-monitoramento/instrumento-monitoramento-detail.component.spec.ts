/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { InstrumentoMonitoramentoDetailComponent } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento-detail.component';
import { InstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

describe('Component Tests', () => {
  describe('InstrumentoMonitoramento Management Detail Component', () => {
    let comp: InstrumentoMonitoramentoDetailComponent;
    let fixture: ComponentFixture<InstrumentoMonitoramentoDetailComponent>;
    const route = ({ data: of({ instrumentoMonitoramento: new InstrumentoMonitoramento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [InstrumentoMonitoramentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InstrumentoMonitoramentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstrumentoMonitoramentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.instrumentoMonitoramento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
