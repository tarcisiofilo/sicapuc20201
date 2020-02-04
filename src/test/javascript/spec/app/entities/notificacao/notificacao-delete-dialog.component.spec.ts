/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NotificacaoDeleteDialogComponent } from 'app/entities/notificacao/notificacao-delete-dialog.component';
import { NotificacaoService } from 'app/entities/notificacao/notificacao.service';

describe('Component Tests', () => {
  describe('Notificacao Management Delete Component', () => {
    let comp: NotificacaoDeleteDialogComponent;
    let fixture: ComponentFixture<NotificacaoDeleteDialogComponent>;
    let service: NotificacaoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NotificacaoDeleteDialogComponent]
      })
        .overrideTemplate(NotificacaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificacaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacaoService);
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
