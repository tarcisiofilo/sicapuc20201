import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { NivelIncidenteService } from './nivel-incidente.service';

@Component({
  selector: 'jhi-nivel-incidente-delete-dialog',
  templateUrl: './nivel-incidente-delete-dialog.component.html'
})
export class NivelIncidenteDeleteDialogComponent {
  nivelIncidente: INivelIncidente;

  constructor(
    protected nivelIncidenteService: NivelIncidenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nivelIncidenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nivelIncidenteListModification',
        content: 'Deleted an nivelIncidente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nivel-incidente-delete-popup',
  template: ''
})
export class NivelIncidenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nivelIncidente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NivelIncidenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.nivelIncidente = nivelIncidente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nivel-incidente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nivel-incidente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
