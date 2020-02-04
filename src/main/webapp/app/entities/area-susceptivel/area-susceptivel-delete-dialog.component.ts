import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { AreaSusceptivelService } from './area-susceptivel.service';

@Component({
  selector: 'jhi-area-susceptivel-delete-dialog',
  templateUrl: './area-susceptivel-delete-dialog.component.html'
})
export class AreaSusceptivelDeleteDialogComponent {
  areaSusceptivel: IAreaSusceptivel;

  constructor(
    protected areaSusceptivelService: AreaSusceptivelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.areaSusceptivelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'areaSusceptivelListModification',
        content: 'Deleted an areaSusceptivel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-area-susceptivel-delete-popup',
  template: ''
})
export class AreaSusceptivelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ areaSusceptivel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AreaSusceptivelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.areaSusceptivel = areaSusceptivel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/area-susceptivel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/area-susceptivel', { outlets: { popup: null } }]);
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
