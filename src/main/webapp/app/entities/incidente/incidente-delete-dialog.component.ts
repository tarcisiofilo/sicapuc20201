import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from './incidente.service';

@Component({
  selector: 'jhi-incidente-delete-dialog',
  templateUrl: './incidente-delete-dialog.component.html'
})
export class IncidenteDeleteDialogComponent {
  incidente: IIncidente;

  constructor(protected incidenteService: IncidenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.incidenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'incidenteListModification',
        content: 'Deleted an incidente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-incidente-delete-popup',
  template: ''
})
export class IncidenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ incidente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IncidenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.incidente = incidente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/incidente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/incidente', { outlets: { popup: null } }]);
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
