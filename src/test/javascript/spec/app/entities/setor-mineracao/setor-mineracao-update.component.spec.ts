/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SetorMineracaoUpdateComponent } from 'app/entities/setor-mineracao/setor-mineracao-update.component';
import { SetorMineracaoService } from 'app/entities/setor-mineracao/setor-mineracao.service';
import { SetorMineracao } from 'app/shared/model/setor-mineracao.model';

describe('Component Tests', () => {
  describe('SetorMineracao Management Update Component', () => {
    let comp: SetorMineracaoUpdateComponent;
    let fixture: ComponentFixture<SetorMineracaoUpdateComponent>;
    let service: SetorMineracaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SetorMineracaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SetorMineracaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SetorMineracaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SetorMineracaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SetorMineracao(123);
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
        const entity = new SetorMineracao();
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
