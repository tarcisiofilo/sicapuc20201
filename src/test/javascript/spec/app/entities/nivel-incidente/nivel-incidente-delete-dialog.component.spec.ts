/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { NivelIncidenteDeleteDialogComponent } from 'app/entities/nivel-incidente/nivel-incidente-delete-dialog.component';
import { NivelIncidenteService } from 'app/entities/nivel-incidente/nivel-incidente.service';

describe('Component Tests', () => {
  describe('NivelIncidente Management Delete Component', () => {
    let comp: NivelIncidenteDeleteDialogComponent;
    let fixture: ComponentFixture<NivelIncidenteDeleteDialogComponent>;
    let service: NivelIncidenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [NivelIncidenteDeleteDialogComponent]
      })
        .overrideTemplate(NivelIncidenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelIncidenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelIncidenteService);
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
