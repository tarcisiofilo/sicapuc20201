/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SireneDetailComponent } from 'app/entities/sirene/sirene-detail.component';
import { Sirene } from 'app/shared/model/sirene.model';

describe('Component Tests', () => {
  describe('Sirene Management Detail Component', () => {
    let comp: SireneDetailComponent;
    let fixture: ComponentFixture<SireneDetailComponent>;
    const route = ({ data: of({ sirene: new Sirene(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SireneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SireneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SireneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sirene).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
