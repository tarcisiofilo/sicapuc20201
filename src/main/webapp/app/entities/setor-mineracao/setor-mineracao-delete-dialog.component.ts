import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { SetorMineracaoService } from './setor-mineracao.service';

@Component({
  selector: 'jhi-setor-mineracao-delete-dialog',
  templateUrl: './setor-mineracao-delete-dialog.component.html'
})
export class SetorMineracaoDeleteDialogComponent {
  setorMineracao: ISetorMineracao;

  constructor(
    protected setorMineracaoService: SetorMineracaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.setorMineracaoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'setorMineracaoListModification',
        content: 'Deleted an setorMineracao'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-setor-mineracao-delete-popup',
  template: ''
})
export class SetorMineracaoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ setorMineracao }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SetorMineracaoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.setorMineracao = setorMineracao;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/setor-mineracao', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/setor-mineracao', { outlets: { popup: null } }]);
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
