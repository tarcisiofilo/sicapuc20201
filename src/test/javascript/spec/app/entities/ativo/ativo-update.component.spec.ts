/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { AtivoUpdateComponent } from 'app/entities/ativo/ativo-update.component';
import { AtivoService } from 'app/entities/ativo/ativo.service';
import { Ativo } from 'app/shared/model/ativo.model';

describe('Component Tests', () => {
  describe('Ativo Management Update Component', () => {
    let comp: AtivoUpdateComponent;
    let fixture: ComponentFixture<AtivoUpdateComponent>;
    let service: AtivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [AtivoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AtivoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AtivoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AtivoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ativo(123);
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
        const entity = new Ativo();
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
