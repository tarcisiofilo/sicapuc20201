/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SireneUpdateComponent } from 'app/entities/sirene/sirene-update.component';
import { SireneService } from 'app/entities/sirene/sirene.service';
import { Sirene } from 'app/shared/model/sirene.model';

describe('Component Tests', () => {
  describe('Sirene Management Update Component', () => {
    let comp: SireneUpdateComponent;
    let fixture: ComponentFixture<SireneUpdateComponent>;
    let service: SireneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SireneUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SireneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SireneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SireneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sirene(123);
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
        const entity = new Sirene();
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
