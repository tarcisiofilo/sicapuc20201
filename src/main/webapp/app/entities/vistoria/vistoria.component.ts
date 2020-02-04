import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVistoria } from 'app/shared/model/vistoria.model';
import { AccountService } from 'app/core';
import { VistoriaService } from './vistoria.service';

@Component({
  selector: 'jhi-vistoria',
  templateUrl: './vistoria.component.html'
})
export class VistoriaComponent implements OnInit, OnDestroy {
  vistorias: IVistoria[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected vistoriaService: VistoriaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.vistoriaService
      .query()
      .pipe(
        filter((res: HttpResponse<IVistoria[]>) => res.ok),
        map((res: HttpResponse<IVistoria[]>) => res.body)
      )
      .subscribe(
        (res: IVistoria[]) => {
          this.vistorias = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVistorias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVistoria) {
    return item.id;
  }

  registerChangeInVistorias() {
    this.eventSubscriber = this.eventManager.subscribe('vistoriaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
