/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NotificacaoUpdateComponent } from 'app/entities/notificacao/notificacao-update.component';
import { NotificacaoService } from 'app/entities/notificacao/notificacao.service';
import { Notificacao } from 'app/shared/model/notificacao.model';

describe('Component Tests', () => {
  describe('Notificacao Management Update Component', () => {
    let comp: NotificacaoUpdateComponent;
    let fixture: ComponentFixture<NotificacaoUpdateComponent>;
    let service: NotificacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NotificacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NotificacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notificacao(123);
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
        const entity = new Notificacao();
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
