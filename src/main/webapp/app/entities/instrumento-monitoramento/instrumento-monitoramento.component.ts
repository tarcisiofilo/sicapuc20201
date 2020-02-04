import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';
import { AccountService } from 'app/core';
import { InstrumentoMonitoramentoService } from './instrumento-monitoramento.service';

@Component({
  selector: 'jhi-instrumento-monitoramento',
  templateUrl: './instrumento-monitoramento.component.html'
})
export class InstrumentoMonitoramentoComponent implements OnInit, OnDestroy {
  instrumentoMonitoramentos: IInstrumentoMonitoramento[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected instrumentoMonitoramentoService: InstrumentoMonitoramentoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.instrumentoMonitoramentoService
      .query()
      .pipe(
        filter((res: HttpResponse<IInstrumentoMonitoramento[]>) => res.ok),
        map((res: HttpResponse<IInstrumentoMonitoramento[]>) => res.body)
      )
      .subscribe(
        (res: IInstrumentoMonitoramento[]) => {
          this.instrumentoMonitoramentos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInstrumentoMonitoramentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInstrumentoMonitoramento) {
    return item.id;
  }

  registerChangeInInstrumentoMonitoramentos() {
    this.eventSubscriber = this.eventManager.subscribe('instrumentoMonitoramentoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
