import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';
import { InstrumentoMonitoramentoService } from './instrumento-monitoramento.service';

@Component({
  selector: 'jhi-instrumento-monitoramento-delete-dialog',
  templateUrl: './instrumento-monitoramento-delete-dialog.component.html'
})
export class InstrumentoMonitoramentoDeleteDialogComponent {
  instrumentoMonitoramento: IInstrumentoMonitoramento;

  constructor(
    protected instrumentoMonitoramentoService: InstrumentoMonitoramentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.instrumentoMonitoramentoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'instrumentoMonitoramentoListModification',
        content: 'Deleted an instrumentoMonitoramento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-instrumento-monitoramento-delete-popup',
  template: ''
})
export class InstrumentoMonitoramentoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ instrumentoMonitoramento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InstrumentoMonitoramentoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.instrumentoMonitoramento = instrumentoMonitoramento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/instrumento-monitoramento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/instrumento-monitoramento', { outlets: { popup: null } }]);
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
