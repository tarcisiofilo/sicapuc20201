/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MedicaoInstrumentoComponent } from 'app/entities/medicao-instrumento/medicao-instrumento.component';
import { MedicaoInstrumentoService } from 'app/entities/medicao-instrumento/medicao-instrumento.service';
import { MedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

describe('Component Tests', () => {
  describe('MedicaoInstrumento Management Component', () => {
    let comp: MedicaoInstrumentoComponent;
    let fixture: ComponentFixture<MedicaoInstrumentoComponent>;
    let service: MedicaoInstrumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MedicaoInstrumentoComponent],
        providers: []
      })
        .overrideTemplate(MedicaoInstrumentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicaoInstrumentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicaoInstrumentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicaoInstrumento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicaoInstrumentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
