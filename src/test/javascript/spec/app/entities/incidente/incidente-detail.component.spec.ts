/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { IncidenteDetailComponent } from 'app/entities/incidente/incidente-detail.component';
import { Incidente } from 'app/shared/model/incidente.model';

describe('Component Tests', () => {
  describe('Incidente Management Detail Component', () => {
    let comp: IncidenteDetailComponent;
    let fixture: ComponentFixture<IncidenteDetailComponent>;
    const route = ({ data: of({ incidente: new Incidente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [IncidenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IncidenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incidente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
