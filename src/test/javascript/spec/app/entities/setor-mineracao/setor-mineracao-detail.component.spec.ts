/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SetorMineracaoDetailComponent } from 'app/entities/setor-mineracao/setor-mineracao-detail.component';
import { SetorMineracao } from 'app/shared/model/setor-mineracao.model';

describe('Component Tests', () => {
  describe('SetorMineracao Management Detail Component', () => {
    let comp: SetorMineracaoDetailComponent;
    let fixture: ComponentFixture<SetorMineracaoDetailComponent>;
    const route = ({ data: of({ setorMineracao: new SetorMineracao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SetorMineracaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SetorMineracaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SetorMineracaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.setorMineracao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
