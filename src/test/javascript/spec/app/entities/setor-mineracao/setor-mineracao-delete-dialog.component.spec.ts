/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { SetorMineracaoDeleteDialogComponent } from 'app/entities/setor-mineracao/setor-mineracao-delete-dialog.component';
import { SetorMineracaoService } from 'app/entities/setor-mineracao/setor-mineracao.service';

describe('Component Tests', () => {
  describe('SetorMineracao Management Delete Component', () => {
    let comp: SetorMineracaoDeleteDialogComponent;
    let fixture: ComponentFixture<SetorMineracaoDeleteDialogComponent>;
    let service: SetorMineracaoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [SetorMineracaoDeleteDialogComponent]
      })
        .overrideTemplate(SetorMineracaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SetorMineracaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SetorMineracaoService);
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
