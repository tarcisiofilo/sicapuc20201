import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INotificacao } from 'app/shared/model/notificacao.model';
import { AccountService } from 'app/core';
import { NotificacaoService } from './notificacao.service';

@Component({
  selector: 'jhi-notificacao',
  templateUrl: './notificacao.component.html'
})
export class NotificacaoComponent implements OnInit, OnDestroy {
  notificacaos: INotificacao[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected notificacaoService: NotificacaoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.notificacaoService
      .query()
      .pipe(
        filter((res: HttpResponse<INotificacao[]>) => res.ok),
        map((res: HttpResponse<INotificacao[]>) => res.body)
      )
      .subscribe(
        (res: INotificacao[]) => {
          this.notificacaos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNotificacaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INotificacao) {
    return item.id;
  }

  registerChangeInNotificacaos() {
    this.eventSubscriber = this.eventManager.subscribe('notificacaoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
