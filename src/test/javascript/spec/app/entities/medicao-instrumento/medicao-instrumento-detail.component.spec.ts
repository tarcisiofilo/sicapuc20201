/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MedicaoInstrumentoDetailComponent } from 'app/entities/medicao-instrumento/medicao-instrumento-detail.component';
import { MedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

describe('Component Tests', () => {
  describe('MedicaoInstrumento Management Detail Component', () => {
    let comp: MedicaoInstrumentoDetailComponent;
    let fixture: ComponentFixture<MedicaoInstrumentoDetailComponent>;
    const route = ({ data: of({ medicaoInstrumento: new MedicaoInstrumento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MedicaoInstrumentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicaoInstrumentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicaoInstrumentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicaoInstrumento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
