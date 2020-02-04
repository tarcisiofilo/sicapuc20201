/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { AreaSusceptivelUpdateComponent } from 'app/entities/area-susceptivel/area-susceptivel-update.component';
import { AreaSusceptivelService } from 'app/entities/area-susceptivel/area-susceptivel.service';
import { AreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

describe('Component Tests', () => {
  describe('AreaSusceptivel Management Update Component', () => {
    let comp: AreaSusceptivelUpdateComponent;
    let fixture: ComponentFixture<AreaSusceptivelUpdateComponent>;
    let service: AreaSusceptivelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [AreaSusceptivelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AreaSusceptivelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AreaSusceptivelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AreaSusceptivelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AreaSusceptivel(123);
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
        const entity = new AreaSusceptivel();
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
