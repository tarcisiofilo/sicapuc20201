import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';
import { MedicaoInstrumentoService } from './medicao-instrumento.service';

@Component({
  selector: 'jhi-medicao-instrumento-delete-dialog',
  templateUrl: './medicao-instrumento-delete-dialog.component.html'
})
export class MedicaoInstrumentoDeleteDialogComponent {
  medicaoInstrumento: IMedicaoInstrumento;

  constructor(
    protected medicaoInstrumentoService: MedicaoInstrumentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicaoInstrumentoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'medicaoInstrumentoListModification',
        content: 'Deleted an medicaoInstrumento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-medicao-instrumento-delete-popup',
  template: ''
})
export class MedicaoInstrumentoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicaoInstrumento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MedicaoInstrumentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.medicaoInstrumento = medicaoInstrumento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/medicao-instrumento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/medicao-instrumento', { outlets: { popup: null } }]);
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
