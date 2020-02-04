/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SetorMineracaoComponent } from 'app/entities/setor-mineracao/setor-mineracao.component';
import { SetorMineracaoService } from 'app/entities/setor-mineracao/setor-mineracao.service';
import { SetorMineracao } from 'app/shared/model/setor-mineracao.model';

describe('Component Tests', () => {
  describe('SetorMineracao Management Component', () => {
    let comp: SetorMineracaoComponent;
    let fixture: ComponentFixture<SetorMineracaoComponent>;
    let service: SetorMineracaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SetorMineracaoComponent],
        providers: []
      })
        .overrideTemplate(SetorMineracaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SetorMineracaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SetorMineracaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SetorMineracao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.setorMineracaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
