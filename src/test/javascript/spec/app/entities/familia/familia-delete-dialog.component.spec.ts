/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { FamiliaDeleteDialogComponent } from 'app/entities/familia/familia-delete-dialog.component';
import { FamiliaService } from 'app/entities/familia/familia.service';

describe('Component Tests', () => {
  describe('Familia Management Delete Component', () => {
    let comp: FamiliaDeleteDialogComponent;
    let fixture: ComponentFixture<FamiliaDeleteDialogComponent>;
    let service: FamiliaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [FamiliaDeleteDialogComponent]
      })
        .overrideTemplate(FamiliaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamiliaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamiliaService);
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
