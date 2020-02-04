import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAtivo } from 'app/shared/model/ativo.model';
import { AccountService } from 'app/core';
import { AtivoService } from './ativo.service';

@Component({
  selector: 'jhi-ativo',
  templateUrl: './ativo.component.html'
})
export class AtivoComponent implements OnInit, OnDestroy {
  ativos: IAtivo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected ativoService: AtivoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.ativoService
      .query()
      .pipe(
        filter((res: HttpResponse<IAtivo[]>) => res.ok),
        map((res: HttpResponse<IAtivo[]>) => res.body)
      )
      .subscribe(
        (res: IAtivo[]) => {
          this.ativos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAtivos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAtivo) {
    return item.id;
  }

  registerChangeInAtivos() {
    this.eventSubscriber = this.eventManager.subscribe('ativoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
