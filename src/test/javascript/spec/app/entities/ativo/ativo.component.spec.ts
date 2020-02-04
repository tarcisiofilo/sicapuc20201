/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { AtivoComponent } from 'app/entities/ativo/ativo.component';
import { AtivoService } from 'app/entities/ativo/ativo.service';
import { Ativo } from 'app/shared/model/ativo.model';

describe('Component Tests', () => {
  describe('Ativo Management Component', () => {
    let comp: AtivoComponent;
    let fixture: ComponentFixture<AtivoComponent>;
    let service: AtivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [AtivoComponent],
        providers: []
      })
        .overrideTemplate(AtivoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AtivoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AtivoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ativo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ativos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
