import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { AccountService } from 'app/core';
import { AreaSusceptivelService } from './area-susceptivel.service';

@Component({
  selector: 'jhi-area-susceptivel',
  templateUrl: './area-susceptivel.component.html'
})
export class AreaSusceptivelComponent implements OnInit, OnDestroy {
  areaSusceptivels: IAreaSusceptivel[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected areaSusceptivelService: AreaSusceptivelService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.areaSusceptivelService
      .query()
      .pipe(
        filter((res: HttpResponse<IAreaSusceptivel[]>) => res.ok),
        map((res: HttpResponse<IAreaSusceptivel[]>) => res.body)
      )
      .subscribe(
        (res: IAreaSusceptivel[]) => {
          this.areaSusceptivels = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAreaSusceptivels();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAreaSusceptivel) {
    return item.id;
  }

  registerChangeInAreaSusceptivels() {
    this.eventSubscriber = this.eventManager.subscribe('areaSusceptivelListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
