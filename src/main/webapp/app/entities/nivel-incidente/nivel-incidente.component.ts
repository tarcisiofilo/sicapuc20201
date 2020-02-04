import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { AccountService } from 'app/core';
import { NivelIncidenteService } from './nivel-incidente.service';

@Component({
  selector: 'jhi-nivel-incidente',
  templateUrl: './nivel-incidente.component.html'
})
export class NivelIncidenteComponent implements OnInit, OnDestroy {
  nivelIncidentes: INivelIncidente[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected nivelIncidenteService: NivelIncidenteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.nivelIncidenteService
      .query()
      .pipe(
        filter((res: HttpResponse<INivelIncidente[]>) => res.ok),
        map((res: HttpResponse<INivelIncidente[]>) => res.body)
      )
      .subscribe(
        (res: INivelIncidente[]) => {
          this.nivelIncidentes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNivelIncidentes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INivelIncidente) {
    return item.id;
  }

  registerChangeInNivelIncidentes() {
    this.eventSubscriber = this.eventManager.subscribe('nivelIncidenteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
