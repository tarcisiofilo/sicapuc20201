/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SireneComponent } from 'app/entities/sirene/sirene.component';
import { SireneService } from 'app/entities/sirene/sirene.service';
import { Sirene } from 'app/shared/model/sirene.model';

describe('Component Tests', () => {
  describe('Sirene Management Component', () => {
    let comp: SireneComponent;
    let fixture: ComponentFixture<SireneComponent>;
    let service: SireneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SireneComponent],
        providers: []
      })
        .overrideTemplate(SireneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SireneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SireneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sirene(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sirenes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
