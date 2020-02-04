import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificacao } from 'app/shared/model/notificacao.model';
import { NotificacaoService } from './notificacao.service';

@Component({
  selector: 'jhi-notificacao-delete-dialog',
  templateUrl: './notificacao-delete-dialog.component.html'
})
export class NotificacaoDeleteDialogComponent {
  notificacao: INotificacao;

  constructor(
    protected notificacaoService: NotificacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.notificacaoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'notificacaoListModification',
        content: 'Deleted an notificacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-notificacao-delete-popup',
  template: ''
})
export class NotificacaoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ notificacao }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NotificacaoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.notificacao = notificacao;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/notificacao', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/notificacao', { outlets: { popup: null } }]);
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
