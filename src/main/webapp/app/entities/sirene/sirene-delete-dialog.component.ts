import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISirene } from 'app/shared/model/sirene.model';
import { SireneService } from './sirene.service';

@Component({
  selector: 'jhi-sirene-delete-dialog',
  templateUrl: './sirene-delete-dialog.component.html'
})
export class SireneDeleteDialogComponent {
  sirene: ISirene;

  constructor(protected sireneService: SireneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sireneService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sireneListModification',
        content: 'Deleted an sirene'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sirene-delete-popup',
  template: ''
})
export class SireneDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sirene }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SireneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sirene = sirene;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sirene', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sirene', { outlets: { popup: null } }]);
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
