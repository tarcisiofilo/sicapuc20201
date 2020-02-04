import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIncidente } from 'app/shared/model/incidente.model';
import { AccountService } from 'app/core';
import { IncidenteService } from './incidente.service';

@Component({
  selector: 'jhi-incidente',
  templateUrl: './incidente.component.html'
})
export class IncidenteComponent implements OnInit, OnDestroy {
  incidentes: IIncidente[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected incidenteService: IncidenteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.incidenteService
      .query()
      .pipe(
        filter((res: HttpResponse<IIncidente[]>) => res.ok),
        map((res: HttpResponse<IIncidente[]>) => res.body)
      )
      .subscribe(
        (res: IIncidente[]) => {
          this.incidentes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInIncidentes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIncidente) {
    return item.id;
  }

  registerChangeInIncidentes() {
    this.eventSubscriber = this.eventManager.subscribe('incidenteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
