/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NotificacaoComponent } from 'app/entities/notificacao/notificacao.component';
import { NotificacaoService } from 'app/entities/notificacao/notificacao.service';
import { Notificacao } from 'app/shared/model/notificacao.model';

describe('Component Tests', () => {
  describe('Notificacao Management Component', () => {
    let comp: NotificacaoComponent;
    let fixture: ComponentFixture<NotificacaoComponent>;
    let service: NotificacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NotificacaoComponent],
        providers: []
      })
        .overrideTemplate(NotificacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Notificacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.notificacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
