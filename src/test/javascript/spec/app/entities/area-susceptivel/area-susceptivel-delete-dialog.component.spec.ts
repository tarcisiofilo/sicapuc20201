/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Sicapuc20201TestModule } from '../../../test.module';
import { AreaSusceptivelDeleteDialogComponent } from 'app/entities/area-susceptivel/area-susceptivel-delete-dialog.component';
import { AreaSusceptivelService } from 'app/entities/area-susceptivel/area-susceptivel.service';

describe('Component Tests', () => {
  describe('AreaSusceptivel Management Delete Component', () => {
    let comp: AreaSusceptivelDeleteDialogComponent;
    let fixture: ComponentFixture<AreaSusceptivelDeleteDialogComponent>;
    let service: AreaSusceptivelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Sicapuc20201TestModule],
        declarations: [AreaSusceptivelDeleteDialogComponent]
      })
        .overrideTemplate(AreaSusceptivelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AreaSusceptivelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AreaSusceptivelService);
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
