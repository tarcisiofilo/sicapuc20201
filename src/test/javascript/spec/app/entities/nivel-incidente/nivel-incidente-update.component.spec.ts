/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NivelIncidenteUpdateComponent } from 'app/entities/nivel-incidente/nivel-incidente-update.component';
import { NivelIncidenteService } from 'app/entities/nivel-incidente/nivel-incidente.service';
import { NivelIncidente } from 'app/shared/model/nivel-incidente.model';

describe('Component Tests', () => {
  describe('NivelIncidente Management Update Component', () => {
    let comp: NivelIncidenteUpdateComponent;
    let fixture: ComponentFixture<NivelIncidenteUpdateComponent>;
    let service: NivelIncidenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NivelIncidenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NivelIncidenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelIncidenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelIncidenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NivelIncidente(123);
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
        const entity = new NivelIncidente();
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
