/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { MedicaoInstrumentoDeleteDialogComponent } from 'app/entities/medicao-instrumento/medicao-instrumento-delete-dialog.component';
import { MedicaoInstrumentoService } from 'app/entities/medicao-instrumento/medicao-instrumento.service';

describe('Component Tests', () => {
  describe('MedicaoInstrumento Management Delete Component', () => {
    let comp: MedicaoInstrumentoDeleteDialogComponent;
    let fixture: ComponentFixture<MedicaoInstrumentoDeleteDialogComponent>;
    let service: MedicaoInstrumentoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [MedicaoInstrumentoDeleteDialogComponent]
      })
        .overrideTemplate(MedicaoInstrumentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicaoInstrumentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicaoInstrumentoService);
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
