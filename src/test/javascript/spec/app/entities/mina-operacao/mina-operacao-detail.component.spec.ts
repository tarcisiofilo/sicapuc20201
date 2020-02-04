/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MinaOperacaoDetailComponent } from 'app/entities/mina-operacao/mina-operacao-detail.component';
import { MinaOperacao } from 'app/shared/model/mina-operacao.model';

describe('Component Tests', () => {
  describe('MinaOperacao Management Detail Component', () => {
    let comp: MinaOperacaoDetailComponent;
    let fixture: ComponentFixture<MinaOperacaoDetailComponent>;
    const route = ({ data: of({ minaOperacao: new MinaOperacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MinaOperacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MinaOperacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MinaOperacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.minaOperacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
