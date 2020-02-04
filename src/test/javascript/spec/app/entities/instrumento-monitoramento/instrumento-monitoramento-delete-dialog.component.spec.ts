/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { InstrumentoMonitoramentoDeleteDialogComponent } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento-delete-dialog.component';
import { InstrumentoMonitoramentoService } from 'app/entities/instrumento-monitoramento/instrumento-monitoramento.service';

describe('Component Tests', () => {
  describe('InstrumentoMonitoramento Management Delete Component', () => {
    let comp: InstrumentoMonitoramentoDeleteDialogComponent;
    let fixture: ComponentFixture<InstrumentoMonitoramentoDeleteDialogComponent>;
    let service: InstrumentoMonitoramentoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [InstrumentoMonitoramentoDeleteDialogComponent]
      })
        .overrideTemplate(InstrumentoMonitoramentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstrumentoMonitoramentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstrumentoMonitoramentoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
