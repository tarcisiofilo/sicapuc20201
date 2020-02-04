/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Sicapuc20201TestModule } from '../../../test.module';
import { InstrumentoMonitoramentoUpdateComponent } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento-update.component';
import { InstrumentoMonitoramentoService } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento.service';
import { InstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

describe('Component Tests', () => {
  describe('InstrumentoMonitoramento Management Update Component', () => {
    let comp: InstrumentoMonitoramentoUpdateComponent;
    let fixture: ComponentFixture<InstrumentoMonitoramentoUpdateComponent>;
    let service: InstrumentoMonitoramentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [InstrumentoMonitoramentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InstrumentoMonitoramentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstrumentoMonitoramentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstrumentoMonitoramentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InstrumentoMonitoramento(123);
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
        const entity = new InstrumentoMonitoramento();
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
