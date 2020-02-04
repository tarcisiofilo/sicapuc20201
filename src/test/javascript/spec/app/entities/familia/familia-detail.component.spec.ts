/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { FamiliaDetailComponent } from 'app/entities/familia/familia-detail.component';
import { Familia } from 'app/shared/model/familia.model';

describe('Component Tests', () => {
  describe('Familia Management Detail Component', () => {
    let comp: FamiliaDetailComponent;
    let fixture: ComponentFixture<FamiliaDetailComponent>;
    const route = ({ data: of({ familia: new Familia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [FamiliaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FamiliaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamiliaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.familia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
