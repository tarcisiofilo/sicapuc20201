import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';
import { MinaOperacaoService } from './mina-operacao.service';

@Component({
  selector: 'jhi-mina-operacao-delete-dialog',
  templateUrl: './mina-operacao-delete-dialog.component.html'
})
export class MinaOperacaoDeleteDialogComponent {
  minaOperacao: IMinaOperacao;

  constructor(
    protected minaOperacaoService: MinaOperacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.minaOperacaoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'minaOperacaoListModification',
        content: 'Deleted an minaOperacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mina-operacao-delete-popup',
  template: ''
})
export class MinaOperacaoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ minaOperacao }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MinaOperacaoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.minaOperacao = minaOperacao;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/mina-operacao', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/mina-operacao', { outlets: { popup: null } }]);
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
