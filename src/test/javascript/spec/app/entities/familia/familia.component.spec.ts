/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { FamiliaComponent } from 'app/entities/familia/familia.component';
import { FamiliaService } from 'app/entities/familia/familia.service';
import { Familia } from 'app/shared/model/familia.model';

describe('Component Tests', () => {
  describe('Familia Management Component', () => {
    let comp: FamiliaComponent;
    let fixture: ComponentFixture<FamiliaComponent>;
    let service: FamiliaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [FamiliaComponent],
        providers: []
      })
        .overrideTemplate(FamiliaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FamiliaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamiliaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Familia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.familias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
