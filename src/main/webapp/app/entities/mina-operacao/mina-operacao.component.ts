import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';
import { AccountService } from 'app/core';
import { MinaOperacaoService } from './mina-operacao.service';

@Component({
  selector: 'jhi-mina-operacao',
  templateUrl: './mina-operacao.component.html'
})
export class MinaOperacaoComponent implements OnInit, OnDestroy {
  minaOperacaos: IMinaOperacao[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected minaOperacaoService: MinaOperacaoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.minaOperacaoService
      .query()
      .pipe(
        filter((res: HttpResponse<IMinaOperacao[]>) => res.ok),
        map((res: HttpResponse<IMinaOperacao[]>) => res.body)
      )
      .subscribe(
        (res: IMinaOperacao[]) => {
          this.minaOperacaos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMinaOperacaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMinaOperacao) {
    return item.id;
  }

  registerChangeInMinaOperacaos() {
    this.eventSubscriber = this.eventManager.subscribe('minaOperacaoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
