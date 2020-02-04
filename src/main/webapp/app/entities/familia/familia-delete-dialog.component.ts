import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilia } from 'app/shared/model/familia.model';
import { FamiliaService } from './familia.service';

@Component({
  selector: 'jhi-familia-delete-dialog',
  templateUrl: './familia-delete-dialog.component.html'
})
export class FamiliaDeleteDialogComponent {
  familia: IFamilia;

  constructor(protected familiaService: FamiliaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.familiaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'familiaListModification',
        content: 'Deleted an familia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-familia-delete-popup',
  template: ''
})
export class FamiliaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ familia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FamiliaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.familia = familia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/familia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/familia', { outlets: { popup: null } }]);
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
