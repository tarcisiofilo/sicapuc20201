/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MedicaoInstrumentoUpdateComponent } from 'app/entities/medicao-instrumento/medicao-instrumento-update.component';
import { MedicaoInstrumentoService } from 'app/entities/medicao-instrumento/medicao-instrumento.service';
import { MedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

describe('Component Tests', () => {
  describe('MedicaoInstrumento Management Update Component', () => {
    let comp: MedicaoInstrumentoUpdateComponent;
    let fixture: ComponentFixture<MedicaoInstrumentoUpdateComponent>;
    let service: MedicaoInstrumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MedicaoInstrumentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicaoInstrumentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicaoInstrumentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicaoInstrumentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicaoInstrumento(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicaoInstrumento();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
