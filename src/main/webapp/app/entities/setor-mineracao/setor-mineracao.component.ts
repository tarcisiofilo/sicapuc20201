import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { AccountService } from 'app/core';
import { SetorMineracaoService } from './setor-mineracao.service';

@Component({
  selector: 'jhi-setor-mineracao',
  templateUrl: './setor-mineracao.component.html'
})
export class SetorMineracaoComponent implements OnInit, OnDestroy {
  setorMineracaos: ISetorMineracao[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected setorMineracaoService: SetorMineracaoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.setorMineracaoService
      .query()
      .pipe(
        filter((res: HttpResponse<ISetorMineracao[]>) => res.ok),
        map((res: HttpResponse<ISetorMineracao[]>) => res.body)
      )
      .subscribe(
        (res: ISetorMineracao[]) => {
          this.setorMineracaos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSetorMineracaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISetorMineracao) {
    return item.id;
  }

  registerChangeInSetorMineracaos() {
    this.eventSubscriber = this.eventManager.subscribe('setorMineracaoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
