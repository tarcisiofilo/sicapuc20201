/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MinaOperacaoUpdateComponent } from 'app/entities/mina-operacao/mina-operacao-update.component';
import { MinaOperacaoService } from 'app/entities/mina-operacao/mina-operacao.service';
import { MinaOperacao } from 'app/shared/model/mina-operacao.model';

describe('Component Tests', () => {
  describe('MinaOperacao Management Update Component', () => {
    let comp: MinaOperacaoUpdateComponent;
    let fixture: ComponentFixture<MinaOperacaoUpdateComponent>;
    let service: MinaOperacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MinaOperacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MinaOperacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MinaOperacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MinaOperacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MinaOperacao(123);
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
        const entity = new MinaOperacao();
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
