/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { AreaSusceptivelDetailComponent } from 'app/entities/area-susceptivel/area-susceptivel-detail.component';
import { AreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

describe('Component Tests', () => {
  describe('AreaSusceptivel Management Detail Component', () => {
    let comp: AreaSusceptivelDetailComponent;
    let fixture: ComponentFixture<AreaSusceptivelDetailComponent>;
    const route = ({ data: of({ areaSusceptivel: new AreaSusceptivel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [AreaSusceptivelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AreaSusceptivelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AreaSusceptivelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.areaSusceptivel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
