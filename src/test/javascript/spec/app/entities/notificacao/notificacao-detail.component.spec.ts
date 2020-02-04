/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NotificacaoDetailComponent } from 'app/entities/notificacao/notificacao-detail.component';
import { Notificacao } from 'app/shared/model/notificacao.model';

describe('Component Tests', () => {
  describe('Notificacao Management Detail Component', () => {
    let comp: NotificacaoDetailComponent;
    let fixture: ComponentFixture<NotificacaoDetailComponent>;
    const route = ({ data: of({ notificacao: new Notificacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NotificacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NotificacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
