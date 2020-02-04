/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MinaOperacaoDeleteDialogComponent } from 'app/entities/mina-operacao/mina-operacao-delete-dialog.component';
import { MinaOperacaoService } from 'app/entities/mina-operacao/mina-operacao.service';

describe('Component Tests', () => {
  describe('MinaOperacao Management Delete Component', () => {
    let comp: MinaOperacaoDeleteDialogComponent;
    let fixture: ComponentFixture<MinaOperacaoDeleteDialogComponent>;
    let service: MinaOperacaoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MinaOperacaoDeleteDialogComponent]
      })
        .overrideTemplate(MinaOperacaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MinaOperacaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MinaOperacaoService);
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
