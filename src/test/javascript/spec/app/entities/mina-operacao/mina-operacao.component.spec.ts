/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MinaOperacaoComponent } from 'app/entities/mina-operacao/mina-operacao.component';
import { MinaOperacaoService } from 'app/entities/mina-operacao/mina-operacao.service';
import { MinaOperacao } from 'app/shared/model/mina-operacao.model';

describe('Component Tests', () => {
  describe('MinaOperacao Management Component', () => {
    let comp: MinaOperacaoComponent;
    let fixture: ComponentFixture<MinaOperacaoComponent>;
    let service: MinaOperacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MinaOperacaoComponent],
        providers: []
      })
        .overrideTemplate(MinaOperacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MinaOperacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MinaOperacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MinaOperacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.minaOperacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
