/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NivelIncidenteDetailComponent } from 'app/entities/nivel-incidente/nivel-incidente-detail.component';
import { NivelIncidente } from 'app/shared/model/nivel-incidente.model';

describe('Component Tests', () => {
  describe('NivelIncidente Management Detail Component', () => {
    let comp: NivelIncidenteDetailComponent;
    let fixture: ComponentFixture<NivelIncidenteDetailComponent>;
    const route = ({ data: of({ nivelIncidente: new NivelIncidente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NivelIncidenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NivelIncidenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelIncidenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nivelIncidente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
