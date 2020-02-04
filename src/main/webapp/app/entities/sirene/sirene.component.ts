import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISirene } from 'app/shared/model/sirene.model';
import { AccountService } from 'app/core';
import { SireneService } from './sirene.service';

@Component({
  selector: 'jhi-sirene',
  templateUrl: './sirene.component.html'
})
export class SireneComponent implements OnInit, OnDestroy {
  sirenes: ISirene[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sireneService: SireneService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sireneService
      .query()
      .pipe(
        filter((res: HttpResponse<ISirene[]>) => res.ok),
        map((res: HttpResponse<ISirene[]>) => res.body)
      )
      .subscribe(
        (res: ISirene[]) => {
          this.sirenes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSirenes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISirene) {
    return item.id;
  }

  registerChangeInSirenes() {
    this.eventSubscriber = this.eventManager.subscribe('sireneListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
