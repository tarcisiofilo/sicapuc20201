import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAtivo } from 'app/shared/model/ativo.model';
import { AtivoService } from './ativo.service';

@Component({
  selector: 'jhi-ativo-delete-dialog',
  templateUrl: './ativo-delete-dialog.component.html'
})
export class AtivoDeleteDialogComponent {
  ativo: IAtivo;

  constructor(protected ativoService: AtivoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ativoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ativoListModification',
        content: 'Deleted an ativo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ativo-delete-popup',
  template: ''
})
export class AtivoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ativo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AtivoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ativo = ativo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ativo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ativo', { outlets: { popup: null } }]);
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
